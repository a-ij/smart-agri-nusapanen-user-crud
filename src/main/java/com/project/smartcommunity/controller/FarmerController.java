package com.project.smartcommunity.controller;

import com.project.smartcommunity.dto.ApiResponse;
import com.project.smartcommunity.dto.FarmerRequest;
import com.project.smartcommunity.model.Farmer;
import com.project.smartcommunity.service.FarmerService;
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
@RequestMapping("/api/farmers")
public class FarmerController {
    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<List<Farmer>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data petani", farmerService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<Farmer>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail petani", farmerService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Farmer>> create(@Valid @RequestBody FarmerRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Petani berhasil dibuat", farmerService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Farmer>> update(@PathVariable Long id, @Valid @RequestBody FarmerRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Petani berhasil diperbarui", farmerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        farmerService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Petani berhasil dihapus", null));
    }
}
