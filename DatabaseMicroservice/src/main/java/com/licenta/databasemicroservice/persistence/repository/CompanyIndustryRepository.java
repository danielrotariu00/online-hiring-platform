package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.CompanyIndustry;
import org.springframework.data.repository.CrudRepository;

public interface CompanyIndustryRepository extends CrudRepository<CompanyIndustry, Integer> {
}
