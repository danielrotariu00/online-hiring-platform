package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyIndustryRepository extends JpaRepository<CompanyIndustry, Long> {
    Optional<CompanyIndustry> findCompanyIndustryByCompanyIdAndIndustryId(Long company_id, Integer industry_id);
    Set<CompanyIndustry> findCompanyIndustriesByCompanyId(Long company_id);
    Set<CompanyIndustry> findCompanyIndustriesByIndustryId(Integer industry_id);
}
