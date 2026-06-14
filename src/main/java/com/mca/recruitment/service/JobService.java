package com.mca.recruitment.service;

import com.mca.recruitment.dto.JobRequest;
import com.mca.recruitment.entity.Job;
import com.mca.recruitment.exception.ResourceNotFoundException;
import com.mca.recruitment.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(JobRequest request) {

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setExperienceRequired(request.getExperienceRequired());
        job.setDescription(request.getDescription());

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));
    }
}