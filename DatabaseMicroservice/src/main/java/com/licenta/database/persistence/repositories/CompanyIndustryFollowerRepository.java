package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.CompanyIndustryFollower;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyIndustryFollowerRepository extends CrudRepository<CompanyIndustryFollower, Integer> {
    Set<CompanyIndustryFollower> findCompanyIndustryFollowersByCompanyIndustryId(Integer companyIndustryId);
    Set<CompanyIndustryFollower> findCompanyIndustryFollowersByUserId(String userId);
    Optional<CompanyIndustryFollower> findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(Integer companyIndustryId, String userId);
}
