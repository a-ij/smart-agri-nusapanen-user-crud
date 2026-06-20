package com.project.smartcommunity.repository;

import com.project.smartcommunity.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
