package com.project.smartcommunity.controller;

import com.project.smartcommunity.dto.ApiResponse;
import com.project.smartcommunity.dto.CommodityRequest;
import com.project.smartcommunity.model.Commodity;
import com.project.smartcommunity.service.CommodityService;
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
@RequestMapping("/api/commodities")
public class CommodityController {
    private final CommodityService commodityService;

    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<List<Commodity>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data komoditas", commodityService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<Commodity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail komoditas", commodityService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Commodity>> create(@Valid @RequestBody CommodityRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Komoditas berhasil dibuat", commodityService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Commodity>> update(@PathVariable Long id, @Valid @RequestBody CommodityRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Komoditas berhasil diperbarui", commodityService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        commodityService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Komoditas berhasil dihapus", null));
    }
}
