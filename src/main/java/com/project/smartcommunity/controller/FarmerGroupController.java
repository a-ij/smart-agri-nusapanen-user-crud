package com.project.smartcommunity.controller;

import com.project.smartcommunity.dto.ApiResponse;
import com.project.smartcommunity.dto.FarmerGroupRequest;
import com.project.smartcommunity.model.FarmerGroup;
import com.project.smartcommunity.service.FarmerGroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class FarmerGroupController {
    private final FarmerGroupService farmerGroupService;

    public FarmerGroupController(FarmerGroupService farmerGroupService) {
        this.farmerGroupService = farmerGroupService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<List<FarmerGroup>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data kelompok tani", farmerGroupService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<FarmerGroup>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail kelompok tani", farmerGroupService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<FarmerGroup>> create(@Valid @RequestBody FarmerGroupRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Kelompok tani berhasil dibuat", farmerGroupService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<FarmerGroup>> update(@PathVariable Long id, @Valid @RequestBody FarmerGroupRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Kelompok tani berhasil diperbarui", farmerGroupService.update(id, request)));
    }

    @PostMapping("/{groupId}/commodities/{commodityId}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<FarmerGroup>> assignCommodity(@PathVariable Long groupId, @PathVariable Long commodityId) {
        return ResponseEntity.ok(ApiResponse.ok("Komoditas berhasil ditambahkan ke kelompok tani", farmerGroupService.assignCommodity(groupId, commodityId)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        farmerGroupService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Kelompok tani berhasil dihapus", null));
    }
}
