package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface JobRepository extends CrudRepository<Job, String> {
    Set<Job> findJobsByCompanyIndustryId(Integer companyIndustryId);
}
