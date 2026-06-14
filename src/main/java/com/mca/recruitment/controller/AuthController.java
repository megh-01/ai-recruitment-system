package com.mca.recruitment.controller;

import com.mca.recruitment.dto.AdminLoginRequest;
import com.mca.recruitment.dto.LoginRequest;
import com.mca.recruitment.dto.LoginResponse;
import com.mca.recruitment.dto.RegisterRequest;
import com.mca.recruitment.entity.User;
import com.mca.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/candidate-login")
    public LoginResponse candidateLogin(@RequestBody LoginRequest request) {
        return userService.candidateLogin(request);
    }

    @PostMapping("/admin-login")
    public LoginResponse adminLogin(@RequestBody AdminLoginRequest request) {
        return userService.adminLogin(request);
    }
}