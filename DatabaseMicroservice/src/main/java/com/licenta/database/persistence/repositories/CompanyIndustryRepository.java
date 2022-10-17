package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.CompanyIndustry;
import org.springframework.data.repository.CrudRepository;

public interface CompanyIndustryRepository extends CrudRepository<CompanyIndustry, Integer> {
}
