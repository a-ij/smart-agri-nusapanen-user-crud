package com.project.smartcommunity.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class FarmerGroupRequest {
    @NotBlank(message = "Nama kelompok tani wajib diisi")
    private String name;

    @NotBlank(message = "Desa wajib diisi")
    private String village;

    private String district;
    private String description;
    private Set<Long> commodityIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getCommodityIds() {
        return commodityIds;
    }

    public void setCommodityIds(Set<Long> commodityIds) {
        this.commodityIds = commodityIds;
    }
}
