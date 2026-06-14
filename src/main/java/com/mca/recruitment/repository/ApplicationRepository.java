package com.mca.recruitment.repository;

import com.mca.recruitment.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    boolean existsByCandidate_IdAndJob_Id(
            Long candidateId,
            Long jobId
    );
}