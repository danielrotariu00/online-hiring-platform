package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.CompanyIndustryFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyIndustryFollowerRepository extends JpaRepository<CompanyIndustryFollower, Long> {
    Set<CompanyIndustryFollower> findCompanyIndustryFollowersByCompanyIndustryId(Long companyIndustryId);
    Set<CompanyIndustryFollower> findCompanyIndustryFollowersByUserId(Long userId);
    void deleteAllByUserId(Long userId);
    Optional<CompanyIndustryFollower> findCompanyIndustryFollowerByCompanyIndustryIdAndUserId(Long companyIndustryId, Long userId);
}
