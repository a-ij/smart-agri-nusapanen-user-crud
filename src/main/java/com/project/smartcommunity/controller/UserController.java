package com.project.smartcommunity.controller;

import com.project.smartcommunity.dto.ApiResponse;
import com.project.smartcommunity.dto.RegisterRequest;
import com.project.smartcommunity.dto.UserUpdateRequest;
import com.project.smartcommunity.model.AppUser;
import com.project.smartcommunity.service.UserService;
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
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppUser>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data user berhasil diambil", userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppUser>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail user berhasil diambil", userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppUser>> create(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("User berhasil dibuat", userService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppUser>> update(@PathVariable Long id,
                                                       @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("User berhasil diperbarui", userService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("User berhasil dihapus", null));
    }
}
