package com.project.smartcommunity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commodities")
public class Commodity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommodityCategory category;

    @Column(nullable = false)
    private String unit;

    @ManyToMany(mappedBy = "commodities")
    @JsonIgnore
    private Set<FarmerGroup> farmerGroups = new HashSet<>();

    @Override
    public String getDisplayName() {
        return "Komoditas: " + name + " (" + unit + ")";
    }

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

    public Set<FarmerGroup> getFarmerGroups() {
        return farmerGroups;
    }

    public void setFarmerGroups(Set<FarmerGroup> farmerGroups) {
        this.farmerGroups = farmerGroups;
    }
}
