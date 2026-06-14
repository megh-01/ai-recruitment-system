package com.mca.recruitment.controller;

import com.mca.recruitment.dto.ApplicationRequest;
import com.mca.recruitment.entity.JobApplication;
import com.mca.recruitment.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public JobApplication applyForJob(@RequestBody ApplicationRequest request) {
        return applicationService.applyForJob(request);
    }

    @GetMapping
    public List<JobApplication> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public JobApplication getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }
}