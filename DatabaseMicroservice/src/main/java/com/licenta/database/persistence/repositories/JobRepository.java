package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.models.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, String> {
}
