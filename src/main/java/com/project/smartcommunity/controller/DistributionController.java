package com.project.smartcommunity.controller;

import com.project.smartcommunity.dto.ApiResponse;
import com.project.smartcommunity.dto.DistributionRequest;
import com.project.smartcommunity.model.Distribution;
import com.project.smartcommunity.service.DistributionService;
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
@RequestMapping("/api/distributions")
public class DistributionController {
    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<List<Distribution>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data distribusi pangan", distributionService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<Distribution>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail distribusi pangan", distributionService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Distribution>> create(@Valid @RequestBody DistributionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Distribusi pangan berhasil dicatat", distributionService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Distribution>> update(@PathVariable Long id, @Valid @RequestBody DistributionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Distribusi pangan berhasil diperbarui", distributionService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        distributionService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Distribusi pangan berhasil dihapus", null));
    }
}
