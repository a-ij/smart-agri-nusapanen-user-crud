package com.project.smartcommunity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FarmerRequest {
    @NotBlank(message = "Nama petani wajib diisi")
    private String fullName;

    @NotBlank(message = "Nomor telepon wajib diisi")
    private String phone;

    @NotBlank(message = "Alamat wajib diisi")
    private String address;

    @NotNull(message = "Kelompok tani wajib dipilih")
    private Long farmerGroupId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getFarmerGroupId() {
        return farmerGroupId;
    }

    public void setFarmerGroupId(Long farmerGroupId) {
        this.farmerGroupId = farmerGroupId;
    }
}
