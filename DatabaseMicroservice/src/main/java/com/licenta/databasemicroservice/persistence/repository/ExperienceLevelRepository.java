package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceLevelRepository extends JpaRepository<ExperienceLevel, Integer> {
}
