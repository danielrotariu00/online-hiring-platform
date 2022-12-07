package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.Industry;
import org.springframework.data.repository.CrudRepository;

public interface IndustryRepository extends CrudRepository<Industry, Integer> {
}
