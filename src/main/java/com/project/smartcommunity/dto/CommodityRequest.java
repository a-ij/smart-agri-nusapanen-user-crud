package com.project.smartcommunity.dto;

import com.project.smartcommunity.model.CommodityCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommodityRequest {
    @NotBlank(message = "Nama komoditas wajib diisi")
    private String name;

    @NotNull(message = "Kategori komoditas wajib dipilih")
    private CommodityCategory category;

    @NotBlank(message = "Satuan wajib diisi, contoh: kg, ton, ikat")
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommodityCategory getCategory() {
        return category;
    }

    public void setCategory(CommodityCategory category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
