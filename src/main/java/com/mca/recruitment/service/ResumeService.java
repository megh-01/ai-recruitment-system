package com.mca.recruitment.service;

import com.mca.recruitment.ai.ResumeParserService;
import com.mca.recruitment.ai.SkillExtractorService;
import com.mca.recruitment.entity.Candidate;
import com.mca.recruitment.entity.Resume;
import com.mca.recruitment.exception.ResourceNotFoundException;
import com.mca.recruitment.repository.CandidateRepository;
import com.mca.recruitment.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ResumeParserService resumeParserService;

    @Autowired
    private SkillExtractorService skillExtractorService;

    private final String uploadDirectory = "uploads/resumes/";

    public Resume uploadResume(Long candidateId, MultipartFile file) throws Exception {

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate not found with id: " + candidateId));

        Files.createDirectories(Paths.get(uploadDirectory));

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            originalFileName = "resume";
        }

        String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path filePath = Paths.get(uploadDirectory + storedFileName);

        Files.write(filePath, file.getBytes());

        String extractedText = resumeParserService.extractTextFromResume(file);
        String extractedSkills = skillExtractorService.extractSkills(extractedText);

        String existingSkills = candidate.getSkills();

        if (extractedSkills != null && !extractedSkills.trim().isEmpty()) {

            if (existingSkills != null && !existingSkills.trim().isEmpty()) {
                candidate.setSkills(existingSkills + ", " + extractedSkills);
            } else {
                candidate.setSkills(extractedSkills);
            }

            candidateRepository.save(candidate);
        }

        Resume resume = new Resume();
        resume.setFileName(originalFileName);
        resume.setFileType(file.getContentType());
        resume.setFilePath(filePath.toString());
        resume.setExtractedText(extractedText);
        resume.setUploadedAt(LocalDateTime.now());
        resume.setCandidate(candidate);

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resume not found with id: " + id));
    }

    public List<Resume> getResumesByCandidateId(Long candidateId) {
        return resumeRepository.findByCandidateId(candidateId);
    }
}