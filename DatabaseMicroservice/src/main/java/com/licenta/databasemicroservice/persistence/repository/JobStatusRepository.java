package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.JobStatus;
import org.springframework.data.repository.CrudRepository;

public interface JobStatusRepository extends CrudRepository<JobStatus, Integer> {
}
