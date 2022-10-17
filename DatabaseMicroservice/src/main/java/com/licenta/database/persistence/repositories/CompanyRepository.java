package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, String> {
    Optional<Company> findCompanyByName(String name);
}
