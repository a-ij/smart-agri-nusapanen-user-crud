package com.project.smartcommunity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "farmer_groups")
public class FarmerGroup extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String village;

    private String district;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "farmerGroup")
    @JsonIgnore
    private List<Farmer> farmers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "farmer_group_commodities",
            joinColumns = @JoinColumn(name = "farmer_group_id"),
            inverseJoinColumns = @JoinColumn(name = "commodity_id")
    )
    private Set<Commodity> commodities = new HashSet<>();

    @Override
    public String getDisplayName() {
        return "Kelompok Tani: " + name;
    }

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

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public void setFarmers(List<Farmer> farmers) {
        this.farmers = farmers;
    }

    public Set<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(Set<Commodity> commodities) {
        this.commodities = commodities;
    }
}
