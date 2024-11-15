package com.example.kzh.services;


import com.example.kzh.dto.request.AuthRequest;
import com.example.kzh.dto.request.RegisterUserRequestDto;
import com.example.kzh.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    void registerUser(RegisterUserRequestDto user);

    AuthResponse authenticateUser(AuthRequest auth);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
