package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.JobType;
import org.springframework.data.repository.CrudRepository;

public interface JobTypeRepository extends CrudRepository<JobType, Integer> {
}
