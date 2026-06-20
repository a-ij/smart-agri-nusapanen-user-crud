package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.CommodityRequest;
import com.project.smartcommunity.exception.BadRequestException;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.Commodity;
import com.project.smartcommunity.repository.CommodityRepository;
import com.project.smartcommunity.service.CommodityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {
    private final CommodityRepository commodityRepository;

    public CommodityServiceImpl(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commodity> findAll() {
        return commodityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Commodity findById(Long id) {
        return commodityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Komoditas tidak ditemukan"));
    }

    @Override
    public Commodity create(CommodityRequest request) {
        if (commodityRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BadRequestException("Nama komoditas sudah ada");
        }
        Commodity commodity = new Commodity();
        fillData(commodity, request);
        return commodityRepository.save(commodity);
    }

    @Override
    public Commodity update(Long id, CommodityRequest request) {
        Commodity commodity = findById(id);
        fillData(commodity, request);
        return commodityRepository.save(commodity);
    }

    @Override
    public void delete(Long id) {
        Commodity commodity = findById(id);
        commodityRepository.delete(commodity);
    }

    private void fillData(Commodity commodity, CommodityRequest request) {
        commodity.setName(request.getName());
        commodity.setCategory(request.getCategory());
        commodity.setUnit(request.getUnit());
    }
}
