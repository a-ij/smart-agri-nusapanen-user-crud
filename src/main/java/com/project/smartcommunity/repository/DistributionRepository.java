package com.project.smartcommunity.repository;

import com.project.smartcommunity.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface DistributionRepository extends JpaRepository<Distribution, Long> {
    @Query("select coalesce(sum(d.quantity), 0) from Distribution d where d.stock.id = :stockId")
    BigDecimal sumDistributedByStockId(@Param("stockId") Long stockId);

    @Query("select coalesce(sum(d.quantity), 0) from Distribution d where d.stock.id = :stockId and (:excludeId is null or d.id <> :excludeId)")
    BigDecimal sumDistributedByStockIdExcept(@Param("stockId") Long stockId, @Param("excludeId") Long excludeId);
}
