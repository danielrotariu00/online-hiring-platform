package com.licenta.jobapplicationmicroservice.persistence.repository;

import com.licenta.jobapplicationmicroservice.persistence.entity.JobApplicationStatus;
import org.springframework.data.repository.CrudRepository;

public interface JobApplicationStatusRepository extends CrudRepository<JobApplicationStatus, Integer> {
}
