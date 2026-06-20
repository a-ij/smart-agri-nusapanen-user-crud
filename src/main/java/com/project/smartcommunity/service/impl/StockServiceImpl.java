package com.project.smartcommunity.service.impl;

import com.project.smartcommunity.dto.StockRequest;
import com.project.smartcommunity.exception.ResourceNotFoundException;
import com.project.smartcommunity.model.Commodity;
import com.project.smartcommunity.model.Farmer;
import com.project.smartcommunity.model.Stock;
import com.project.smartcommunity.repository.CommodityRepository;
import com.project.smartcommunity.repository.FarmerRepository;
import com.project.smartcommunity.repository.StockRepository;
import com.project.smartcommunity.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final FarmerRepository farmerRepository;
    private final CommodityRepository commodityRepository;

    public StockServiceImpl(StockRepository stockRepository,
                            FarmerRepository farmerRepository,
                            CommodityRepository commodityRepository) {
        this.stockRepository = stockRepository;
        this.farmerRepository = farmerRepository;
        this.commodityRepository = commodityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stok tidak ditemukan"));
    }

    @Override
    public Stock create(StockRequest request) {
        Stock stock = new Stock();
        fillData(stock, request);
        return stockRepository.save(stock);
    }

    @Override
    public Stock update(Long id, StockRequest request) {
        Stock stock = findById(id);
        fillData(stock, request);
        return stockRepository.save(stock);
    }

    @Override
    public void delete(Long id) {
        Stock stock = findById(id);
        stockRepository.delete(stock);
    }

    private void fillData(Stock stock, StockRequest request) {
        Farmer farmer = farmerRepository.findById(request.getFarmerId())
                .orElseThrow(() -> new ResourceNotFoundException("Petani tidak ditemukan"));
        Commodity commodity = commodityRepository.findById(request.getCommodityId())
                .orElseThrow(() -> new ResourceNotFoundException("Komoditas tidak ditemukan"));

        stock.setFarmer(farmer);
        stock.setCommodity(commodity);
        stock.setQuantity(request.getQuantity());
        stock.setHarvestDate(request.getHarvestDate());
        stock.setLocation(request.getLocation());
    }
}
