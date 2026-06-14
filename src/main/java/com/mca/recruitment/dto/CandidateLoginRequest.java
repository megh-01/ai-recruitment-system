package com.mca.recruitment.dto;

public class CandidateLoginRequest {

    private Long userId;
    private String password;

    public CandidateLoginRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}