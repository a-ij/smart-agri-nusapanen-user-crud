package com.project.smartcommunity.controller;

import com.project.smartcommunity.dto.ApiResponse;
import com.project.smartcommunity.dto.StockRequest;
import com.project.smartcommunity.model.Stock;
import com.project.smartcommunity.service.StockService;
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
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<List<Stock>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data stok pangan", stockService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<Stock>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail stok pangan", stockService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS','PETANI')")
    public ResponseEntity<ApiResponse<Stock>> create(@Valid @RequestBody StockRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Stok pangan berhasil dicatat", stockService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PETUGAS')")
    public ResponseEntity<ApiResponse<Stock>> update(@PathVariable Long id, @Valid @RequestBody StockRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Stok pangan berhasil diperbarui", stockService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Stok pangan berhasil dihapus", null));
    }
}
