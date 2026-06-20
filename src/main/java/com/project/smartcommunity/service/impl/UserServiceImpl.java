package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.RegisterRequest;
import com.project.smartcommunity.dto.UserUpdateRequest;
import com.project.smartcommunity.exception.BadRequestException;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.AppUser;
import com.project.smartcommunity.model.Farmer;
import com.project.smartcommunity.repository.AppUserRepository;
import com.project.smartcommunity.repository.FarmerRepository;
import com.project.smartcommunity.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AppUserRepository appUserRepository,
                           FarmerRepository farmerRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.farmerRepository = farmerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser findById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));
    }

    @Override
    public AppUser create(RegisterRequest request) {
        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username sudah digunakan");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setFarmer(resolveFarmer(request.getFarmerId()));
        return appUserRepository.save(user);
    }

    @Override
    public AppUser update(Long id, UserUpdateRequest request) {
        AppUser user = findById(id);

        if (appUserRepository.existsByUsernameAndIdNot(request.getUsername(), id)) {
            throw new BadRequestException("Username sudah digunakan user lain");
        }

        user.setUsername(request.getUsername());
        user.setRole(request.getRole());
        user.setFarmer(resolveFarmer(request.getFarmerId()));

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return appUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        AppUser user = findById(id);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (user.getUsername().equals(currentUsername)) {
            throw new BadRequestException("User yang sedang login tidak boleh menghapus akunnya sendiri");
        }

        appUserRepository.delete(user);
    }

    private Farmer resolveFarmer(Long farmerId) {
        if (farmerId == null) {
            return null;
        }

        return farmerRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("Petani tidak ditemukan"));
    }
}
