package com.mca.recruitment.dto;

public class AdminLoginRequest {

    private String adminId;
    private String password;

    public AdminLoginRequest() {
    }

    public String getAdminId() {
        return adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}