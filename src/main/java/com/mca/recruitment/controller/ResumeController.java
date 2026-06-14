package com.mca.recruitment.controller;

import com.mca.recruitment.entity.Resume;
import com.mca.recruitment.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload/{candidateId}")
    public Resume uploadResume(
            @PathVariable Long candidateId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        return resumeService.uploadResume(candidateId, file);
    }

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }

    @GetMapping("/{id}")
    public Resume getResumeById(@PathVariable Long id) {
        return resumeService.getResumeById(id);
    }

    @GetMapping("/candidate/{candidateId}")
    public List<Resume> getResumesByCandidateId(@PathVariable Long candidateId) {
        return resumeService.getResumesByCandidateId(candidateId);
    }
}