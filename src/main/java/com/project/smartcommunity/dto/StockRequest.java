package com.project.smartcommunity.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StockRequest {
    @NotNull(message = "Petani wajib dipilih")
    private Long farmerId;

    @NotNull(message = "Komoditas wajib dipilih")
    private Long commodityId;

    @NotNull(message = "Jumlah stok wajib diisi")
    @DecimalMin(value = "0.01", message = "Jumlah stok harus lebih dari 0")
    private BigDecimal quantity;

    @NotNull(message = "Tanggal panen wajib diisi")
    private LocalDate harvestDate;

    private String location;

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDate getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(LocalDate harvestDate) {
        this.harvestDate = harvestDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
