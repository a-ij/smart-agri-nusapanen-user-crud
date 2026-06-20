package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.MonitoringResponse;
import com.project.smartcommunity.model.Commodity;
import com.project.smartcommunity.model.Distribution;
import com.project.smartcommunity.model.Stock;
import com.project.smartcommunity.repository.CommodityRepository;
import com.project.smartcommunity.repository.DistributionRepository;
import com.project.smartcommunity.repository.StockRepository;
import com.project.smartcommunity.service.MonitoringService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class MonitoringServiceImpl implements MonitoringService {
    private final CommodityRepository commodityRepository;
    private final StockRepository stockRepository;
    private final DistributionRepository distributionRepository;

    public MonitoringServiceImpl(CommodityRepository commodityRepository,
                                 StockRepository stockRepository,
                                 DistributionRepository distributionRepository) {
        this.commodityRepository = commodityRepository;
        this.stockRepository = stockRepository;
        this.distributionRepository = distributionRepository;
    }

    @Override
    public List<MonitoringResponse> getFoodAvailability() {
        Map<Long, BigDecimal> totalStockByCommodity = new HashMap<>();
        Map<Long, BigDecimal> totalDistributedByCommodity = new HashMap<>();

        for (Stock stock : stockRepository.findAll()) {
            Long commodityId = stock.getCommodity().getId();
            totalStockByCommodity.merge(commodityId, stock.getQuantity(), BigDecimal::add);
        }

        for (Distribution distribution : distributionRepository.findAll()) {
            Long commodityId = distribution.getStock().getCommodity().getId();
            totalDistributedByCommodity.merge(commodityId, distribution.getQuantity(), BigDecimal::add);
        }

        List<MonitoringResponse> responses = new ArrayList<>();
        for (Commodity commodity : commodityRepository.findAll()) {
            BigDecimal totalStock = totalStockByCommodity.getOrDefault(commodity.getId(), BigDecimal.ZERO);
            BigDecimal totalDistributed = totalDistributedByCommodity.getOrDefault(commodity.getId(), BigDecimal.ZERO);
            BigDecimal available = totalStock.subtract(totalDistributed);
            String status = calculateStatus(available);

            responses.add(new MonitoringResponse(
                    commodity.getId(),
                    commodity.getName(),
                    commodity.getUnit(),
                    totalStock,
                    totalDistributed,
                    available,
                    status
            ));
        }
        return responses;
    }

    private String calculateStatus(BigDecimal available) {
        if (available.compareTo(BigDecimal.ZERO) <= 0) {
            return "HABIS";
        }
        if (available.compareTo(new BigDecimal("50")) <= 0) {
            return "MENIPIS";
        }
        return "AMAN";
    }
}
