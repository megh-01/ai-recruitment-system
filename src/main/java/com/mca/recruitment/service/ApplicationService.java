package com.mca.recruitment.service;

import com.mca.recruitment.ai.ResumeMatcherService;
import com.mca.recruitment.dto.ApplicationRequest;
import com.mca.recruitment.entity.ApplicationStatus;
import com.mca.recruitment.entity.Candidate;
import com.mca.recruitment.entity.Job;
import com.mca.recruitment.entity.JobApplication;
import com.mca.recruitment.exception.ResourceNotFoundException;
import com.mca.recruitment.repository.ApplicationRepository;
import com.mca.recruitment.repository.CandidateRepository;
import com.mca.recruitment.repository.JobRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final ResumeMatcherService resumeMatcherService;
    private final EmailService emailService;

    @Value("${app.selection-threshold:85.0}")
    private double selectionThreshold;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CandidateRepository candidateRepository,
            JobRepository jobRepository,
            ResumeMatcherService resumeMatcherService,
            EmailService emailService) {

        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.resumeMatcherService = resumeMatcherService;
        this.emailService = emailService;
    }

    public JobApplication applyForJob(ApplicationRequest request) {

        if (request == null
                || request.getCandidateId() == null
                || request.getJobId() == null) {

            throw new IllegalArgumentException(
                    "Candidate ID and Job ID are required"
            );
        }

        boolean alreadyApplied =
                applicationRepository.existsByCandidate_IdAndJob_Id(
                        request.getCandidateId(),
                        request.getJobId()
                );

        if (alreadyApplied) {
            throw new RuntimeException(
                    "Candidate has already applied for this job"
            );
        }

        Candidate candidate = candidateRepository
                .findById(request.getCandidateId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found with id: "
                                        + request.getCandidateId()
                        )
                );

        Job job = jobRepository
                .findById(request.getJobId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: "
                                        + request.getJobId()
                        )
                );

        double matchPercentage =
                resumeMatcherService.calculateMatch(
                        candidate.getSkills(),
                        job.getRequiredSkills()
                );

        /*
         * 85% and above = selected.
         * Below 85% = rejected.
         */
        boolean selected =
                matchPercentage >= selectionThreshold;

        ApplicationStatus finalStatus =
                selected
                        ? ApplicationStatus.SELECTED
                        : ApplicationStatus.REJECTED;

        JobApplication application = new JobApplication();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus(finalStatus);
        application.setMatchPercentage(matchPercentage);

        JobApplication savedApplication =
                applicationRepository.save(application);

        sendResultEmail(
                candidate,
                job,
                matchPercentage,
                selected
        );

        return savedApplication;
    }

    private void sendResultEmail(
            Candidate candidate,
            Job job,
            double matchPercentage,
            boolean selected) {

        if (candidate.getUser() == null) {
            System.err.println(
                    "Email not sent: candidate has no linked user account."
            );
            return;
        }

        String candidateEmail =
                candidate.getUser().getEmail();

        if (candidateEmail == null
                || candidateEmail.isBlank()) {

            System.err.println(
                    "Email not sent: candidate email is empty."
            );
            return;
        }

        try {
            emailService.sendApplicationResult(
                    candidateEmail,
                    candidate.getFullName(),
                    job.getTitle(),
                    job.getCompanyName(),
                    matchPercentage,
                    selectionThreshold,
                    selected
            );

            System.out.println(
                    "Application result email sent to: "
                            + candidateEmail
            );

        } catch (MailException exception) {

            /*
             * Application remains saved even when email delivery fails.
             * Check the STS console for the SMTP error.
             */
            System.err.println(
                    "Application saved, but email could not be sent to "
                            + candidateEmail
                            + ". Reason: "
                            + exception.getMessage()
            );
        }
    }

    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    public JobApplication getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: " + id
                        )
                );
    }
}