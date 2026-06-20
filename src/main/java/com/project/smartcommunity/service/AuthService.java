package com.project.smartcommunity.service;

import com.project.smartcommunity.dto.LoginRequest;
import com.project.smartcommunity.dto.LoginResponse;
import com.project.smartcommunity.dto.RegisterRequest;
import com.project.smartcommunity.model.AppUser;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    AppUser register(RegisterRequest request);
}
