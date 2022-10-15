package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.models.CompanyFollower;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CompanyFollowerRepository extends CrudRepository<CompanyFollower, Integer> {
    Set<CompanyFollower> findCompanyFollowersByCompany_Id(String companyId);
    Set<CompanyFollower> findCompanyFollowersByUser_Id(String userId);

    Optional<CompanyFollower> findByCompany_IdAndUser_Id(String companyId, String userId);
}
