package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.CompanyIndustryFollower;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyIndustryFollowerRepository extends CrudRepository<CompanyIndustryFollower, Long> {
    Set<CompanyIndustryFollower> findCompanyIndustryFollowersByCompanyIndustryId(Long companyIndustryId);
    Set<CompanyIndustryFollower> findCompanyIndustryFollowersByUserId(Long userId);
    Optional<CompanyIndustryFollower> findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(Long companyIndustryId, Long userId);
}
