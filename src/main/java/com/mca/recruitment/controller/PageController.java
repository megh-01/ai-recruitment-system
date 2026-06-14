package com.mca.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/candidate-register")
    public String candidateRegisterPage() {
        return "candidate-register";
    }

    @GetMapping("/candidate-login")
    public String candidateLoginPage() {
        return "candidate-login";
    }

    @GetMapping("/candidate-home")
    public String candidateHomePage() {
        return "candidate-home";
    }

    @GetMapping("/recruiter-register")
    public String recruiterRegisterPage() {
        return "recruiter-register";
    }

    @GetMapping("/recruiter-home")
    public String recruiterHomePage() {
        return "recruiter-home";
    }

    @GetMapping("/admin-login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboardPage() {
        return "admin-dashboard";
    }
}