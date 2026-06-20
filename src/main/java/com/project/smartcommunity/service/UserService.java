package com.project.smartcommunity.service;

import com.project.smartcommunity.dto.RegisterRequest;
import com.project.smartcommunity.dto.UserUpdateRequest;
import com.project.smartcommunity.model.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> findAll();
    AppUser findById(Long id);
    AppUser create(RegisterRequest request);
    AppUser update(Long id, UserUpdateRequest request);
    void delete(Long id);
}
