package com.project.smartcommunity.repository;

import com.project.smartcommunity.model.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
