package com.mca.recruitment.controller;

import com.mca.recruitment.dto.CandidateRequest;
import com.mca.recruitment.entity.Candidate;
import com.mca.recruitment.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public Candidate createCandidate(@RequestBody CandidateRequest request) {
        return candidateService.createCandidate(request);
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/user/{userId}")
    public Candidate getCandidateByUserId(@PathVariable Long userId) {
        return candidateService.getCandidateByUserId(userId);
    }
}