package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findCompanyByName(String name);
}
