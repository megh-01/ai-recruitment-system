package com.mca.recruitment.service;

import com.mca.recruitment.dto.AdminLoginRequest;
import com.mca.recruitment.dto.LoginRequest;
import com.mca.recruitment.dto.LoginResponse;
import com.mca.recruitment.dto.RegisterRequest;
import com.mca.recruitment.entity.Role;
import com.mca.recruitment.entity.User;
import com.mca.recruitment.exception.ResourceNotFoundException;
import com.mca.recruitment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    public LoginResponse candidateLogin(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (user.getRole() != Role.CANDIDATE) {
            throw new RuntimeException("This login is only for candidates");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setMessage("Candidate login successful");

        return response;
    }

    public LoginResponse adminLogin(AdminLoginRequest request) {

        String fixedAdminId = "admin";
        String fixedPassword = "admin123";

        if (!fixedAdminId.equals(request.getAdminId()) || !fixedPassword.equals(request.getPassword())) {
            throw new RuntimeException("Invalid admin ID or password");
        }

        LoginResponse response = new LoginResponse();
        response.setUserId(0L);
        response.setName("System Admin");
        response.setEmail("admin@airecruittrack.com");
        response.setRole(Role.ADMIN);
        response.setMessage("Admin login successful");

        return response;
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}