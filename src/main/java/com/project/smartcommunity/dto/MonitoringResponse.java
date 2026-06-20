package com.project.smartcommunity.dto;

import java.math.BigDecimal;

public class MonitoringResponse {
    private Long commodityId;
    private String commodityName;
    private String unit;
    private BigDecimal totalStock;
    private BigDecimal totalDistributed;
    private BigDecimal availableStock;
    private String status;

    public MonitoringResponse(Long commodityId, String commodityName, String unit,
                              BigDecimal totalStock, BigDecimal totalDistributed,
                              BigDecimal availableStock, String status) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.unit = unit;
        this.totalStock = totalStock;
        this.totalDistributed = totalDistributed;
        this.availableStock = availableStock;
        this.status = status;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(BigDecimal totalStock) {
        this.totalStock = totalStock;
    }

    public BigDecimal getTotalDistributed() {
        return totalDistributed;
    }

    public void setTotalDistributed(BigDecimal totalDistributed) {
        this.totalDistributed = totalDistributed;
    }

    public BigDecimal getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(BigDecimal availableStock) {
        this.availableStock = availableStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
