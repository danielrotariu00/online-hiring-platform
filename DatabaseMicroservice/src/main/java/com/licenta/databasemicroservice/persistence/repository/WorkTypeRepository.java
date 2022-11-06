package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.WorkType;
import org.springframework.data.repository.CrudRepository;

public interface WorkTypeRepository extends CrudRepository<WorkType, Integer> {
}
