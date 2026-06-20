package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.FarmerGroupRequest;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.Commodity;
import com.project.smartcommunity.model.FarmerGroup;
import com.project.smartcommunity.repository.CommodityRepository;
import com.project.smartcommunity.repository.FarmerGroupRepository;
import com.project.smartcommunity.service.FarmerGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class FarmerGroupServiceImpl implements FarmerGroupService {
    private final FarmerGroupRepository farmerGroupRepository;
    private final CommodityRepository commodityRepository;

    public FarmerGroupServiceImpl(FarmerGroupRepository farmerGroupRepository,
                                  CommodityRepository commodityRepository) {
        this.farmerGroupRepository = farmerGroupRepository;
        this.commodityRepository = commodityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmerGroup> findAll() {
        return farmerGroupRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerGroup findById(Long id) {
        return farmerGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kelompok tani tidak ditemukan"));
    }

    @Override
    public FarmerGroup create(FarmerGroupRequest request) {
        FarmerGroup group = new FarmerGroup();
        fillData(group, request);
        return farmerGroupRepository.save(group);
    }

    @Override
    public FarmerGroup update(Long id, FarmerGroupRequest request) {
        FarmerGroup group = findById(id);
        fillData(group, request);
        return farmerGroupRepository.save(group);
    }

    @Override
    public void delete(Long id) {
        FarmerGroup group = findById(id);
        farmerGroupRepository.delete(group);
    }

    @Override
    public FarmerGroup assignCommodity(Long groupId, Long commodityId) {
        FarmerGroup group = findById(groupId);
        Commodity commodity = commodityRepository.findById(commodityId)
                .orElseThrow(() -> new ResourceNotFoundException("Komoditas tidak ditemukan"));
        group.getCommodities().add(commodity);
        return farmerGroupRepository.save(group);
    }

    private void fillData(FarmerGroup group, FarmerGroupRequest request) {
        group.setName(request.getName());
        group.setVillage(request.getVillage());
        group.setDistrict(request.getDistrict());
        group.setDescription(request.getDescription());

        if (request.getCommodityIds() != null) {
            group.setCommodities(new HashSet<>(commodityRepository.findAllById(request.getCommodityIds())));
        }
    }
}
