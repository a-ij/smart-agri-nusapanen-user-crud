package com.project.smartcommunity.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DistributionRequest {
    @NotNull(message = "Stok wajib dipilih")
    private Long stockId;

    @NotNull(message = "Jumlah distribusi wajib diisi")
    @DecimalMin(value = "0.01", message = "Jumlah distribusi harus lebih dari 0")
    private BigDecimal quantity;

    @NotBlank(message = "Tujuan distribusi wajib diisi")
    private String destination;

    private String receiverName;

    @NotNull(message = "Tanggal distribusi wajib diisi")
    private LocalDate distributionDate;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public LocalDate getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(LocalDate distributionDate) {
        this.distributionDate = distributionDate;
    }
}
