package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.CompanyManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyManagerRepository extends JpaRepository<CompanyManager, Long> {
    Optional<CompanyManager> findCompanyManagerByCompanyIdAndManagerId(Long companyId, Long managerId);
    Set<CompanyManager> findCompanyManagersByCompanyId(Long companyId);
}
