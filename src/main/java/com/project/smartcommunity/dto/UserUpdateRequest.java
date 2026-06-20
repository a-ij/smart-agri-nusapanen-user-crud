package com.project.smartcommunity.dto;

import com.project.smartcommunity.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest {
    @NotBlank(message = "Username wajib diisi")
    private String username;

    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

    @NotNull(message = "Role wajib dipilih")
    private Role role;

    private Long farmerId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }
}
