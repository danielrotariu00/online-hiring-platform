package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {
}
