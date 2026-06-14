package com.mca.recruitment.service;

import com.mca.recruitment.dto.CandidateRequest;
import com.mca.recruitment.entity.Candidate;
import com.mca.recruitment.entity.User;
import com.mca.recruitment.exception.ResourceNotFoundException;
import com.mca.recruitment.repository.CandidateRepository;
import com.mca.recruitment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private CandidateRepository candidateRepository;
    private UserRepository userRepository;

    public CandidateService(CandidateRepository candidateRepository,
                            UserRepository userRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    public Candidate createCandidate(CandidateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + request.getUserId()));

        Candidate candidate = new Candidate();

        candidate.setFullName(request.getFullName());
        candidate.setPhone(request.getPhone());
        candidate.setEducation(request.getEducation());
        candidate.setSkills(request.getSkills());
        candidate.setExperience(request.getExperience());
        candidate.setUser(user);

        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateByUserId(Long userId) {

        return candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate profile not found for user id: " + userId));
    }
}