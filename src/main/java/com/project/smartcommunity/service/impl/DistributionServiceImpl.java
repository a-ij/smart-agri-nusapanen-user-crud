package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.DistributionRequest;
import com.project.smartcommunity.exception.BadRequestException;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.Distribution;
import com.project.smartcommunity.model.Stock;
import com.project.smartcommunity.repository.DistributionRepository;
import com.project.smartcommunity.repository.StockRepository;
import com.project.smartcommunity.service.DistributionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class DistributionServiceImpl implements DistributionService {
    private final DistributionRepository distributionRepository;
    private final StockRepository stockRepository;

    public DistributionServiceImpl(DistributionRepository distributionRepository, StockRepository stockRepository) {
        this.distributionRepository = distributionRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Distribution> findAll() {
        return distributionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Distribution findById(Long id) {
        return distributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribusi tidak ditemukan"));
    }

    @Override
    public Distribution create(DistributionRequest request) {
        Distribution distribution = new Distribution();
        fillData(distribution, request);
        return distributionRepository.save(distribution);
    }

    @Override
    public Distribution update(Long id, DistributionRequest request) {
        Distribution distribution = findById(id);
        fillData(distribution, request);
        return distributionRepository.save(distribution);
    }

    @Override
    public void delete(Long id) {
        Distribution distribution = findById(id);
        distributionRepository.delete(distribution);
    }

    private void fillData(Distribution distribution, DistributionRequest request) {
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new ResourceNotFoundException("Stok tidak ditemukan"));

        BigDecimal distributed = distributionRepository.sumDistributedByStockIdExcept(stock.getId(), distribution.getId());
        BigDecimal available = stock.getQuantity().subtract(distributed);

        if (request.getQuantity().compareTo(available) > 0) {
            throw new BadRequestException("Jumlah distribusi melebihi stok tersedia. Stok tersedia: " + available);
        }

        distribution.setStock(stock);
        distribution.setQuantity(request.getQuantity());
        distribution.setDestination(request.getDestination());
        distribution.setReceiverName(request.getReceiverName());
        distribution.setDistributionDate(request.getDistributionDate());
    }
}
