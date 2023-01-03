package com.licenta.jobapplicationmicroservice.persistence.repository;

import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobApplicationRepository extends CrudRepository<JobApplication, Long> {
    Iterable<JobApplication> findAllByUserId(Long userId);
    Iterable<JobApplication> findAllByJobId(Long jobId);
    Optional<JobApplication> findByUserIdAndJobId(Long userId, Long jobId);
}
