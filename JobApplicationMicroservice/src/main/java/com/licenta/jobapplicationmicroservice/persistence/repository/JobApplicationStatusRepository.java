package com.licenta.jobapplicationmicroservice.persistence.repository;

import com.licenta.jobapplicationmicroservice.persistence.document.JobApplicationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobApplicationStatusRepository extends MongoRepository<JobApplicationStatus, Integer> {
}
