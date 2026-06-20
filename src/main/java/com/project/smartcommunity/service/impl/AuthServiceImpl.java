package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.config.JwtService;
import com.project.smartcommunity.dto.LoginRequest;
import com.project.smartcommunity.dto.LoginResponse;
import com.project.smartcommunity.dto.RegisterRequest;
import com.project.smartcommunity.exception.BadRequestException;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.AppUser;
import com.project.smartcommunity.model.Farmer;
import com.project.smartcommunity.repository.AppUserRepository;
import com.project.smartcommunity.repository.FarmerRepository;
import com.project.smartcommunity.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           AppUserRepository appUserRepository,
                           FarmerRepository farmerRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.farmerRepository = farmerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        AppUser user = appUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        String token = jwtService.generateToken(user);
        return new LoginResponse(token, user.getUsername(), user.getRole());
    }

    @Override
    public AppUser register(RegisterRequest request) {
        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username sudah digunakan");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        if (request.getFarmerId() != null) {
            Farmer farmer = farmerRepository.findById(request.getFarmerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Petani tidak ditemukan"));
            user.setFarmer(farmer);
        }

        return appUserRepository.save(user);
    }
}
