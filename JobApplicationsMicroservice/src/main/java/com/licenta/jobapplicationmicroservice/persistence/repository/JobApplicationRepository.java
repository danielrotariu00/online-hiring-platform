package com.licenta.jobapplicationmicroservice.persistence.repository;

import com.licenta.jobapplicationmicroservice.persistence.document.JobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends MongoRepository<JobApplication, String> {

    @Query("{userId: ?0}")
    Iterable<JobApplication> findAllByUserId(Long userId);

    @Query("{'job.id': ?0}")
    Iterable<JobApplication> findAllByJobId(Long jobId);

    @Query("{userId: ?0, 'job.id': ?1}")
    Optional<JobApplication> findByUserIdAndJobId(Long userId, Long jobId);

    @Query("{'job.recruiterId': ?0}")
    List<JobApplication> findAllByRecruiterId(Long recruiterId);
}
