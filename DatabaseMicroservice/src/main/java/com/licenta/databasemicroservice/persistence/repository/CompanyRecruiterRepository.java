package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.CompanyRecruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyRecruiterRepository extends JpaRepository<CompanyRecruiter, Long> {
    Optional<CompanyRecruiter> findCompanyRecruiterByCompanyIdAndRecruiterId(Long companyId, Long recruiterId);
    Set<CompanyRecruiter> findCompanyRecruitersByCompanyId(Long companyId);
}
