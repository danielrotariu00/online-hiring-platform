package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface JobRepository extends CrudRepository<Job, Long> {
    Set<Job> findJobsByCompanyIndustryId(Long companyIndustryId);
}
