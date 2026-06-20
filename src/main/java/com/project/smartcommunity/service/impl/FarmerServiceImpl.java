package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.FarmerRequest;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.Farmer;
import com.project.smartcommunity.model.FarmerGroup;
import com.project.smartcommunity.repository.FarmerGroupRepository;
import com.project.smartcommunity.repository.FarmerRepository;
import com.project.smartcommunity.service.FarmerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FarmerServiceImpl implements FarmerService {
    private final FarmerRepository farmerRepository;
    private final FarmerGroupRepository farmerGroupRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository, FarmerGroupRepository farmerGroupRepository) {
        this.farmerRepository = farmerRepository;
        this.farmerGroupRepository = farmerGroupRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmer> findAll() {
        return farmerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Farmer findById(Long id) {
        return farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petani tidak ditemukan"));
    }

    @Override
    public Farmer create(FarmerRequest request) {
        Farmer farmer = new Farmer();
        fillData(farmer, request);
        return farmerRepository.save(farmer);
    }

    @Override
    public Farmer update(Long id, FarmerRequest request) {
        Farmer farmer = findById(id);
        fillData(farmer, request);
        return farmerRepository.save(farmer);
    }

    @Override
    public void delete(Long id) {
        Farmer farmer = findById(id);
        farmerRepository.delete(farmer);
    }

    private void fillData(Farmer farmer, FarmerRequest request) {
        FarmerGroup group = farmerGroupRepository.findById(request.getFarmerGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Kelompok tani tidak ditemukan"));

        farmer.setFullName(request.getFullName());
        farmer.setPhone(request.getPhone());
        farmer.setAddress(request.getAddress());
        farmer.setFarmerGroup(group);
    }
}
