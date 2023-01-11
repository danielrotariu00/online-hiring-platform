package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface JobRepository extends JpaRepository<Job, Long> {
    Set<Job> findJobsByCompanyIndustryId(Long companyIndustryId);
}
