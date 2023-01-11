package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.UserEducationalExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEducationalExperienceRepository extends JpaRepository<UserEducationalExperience, Long> {
    List<UserEducationalExperience> findAllByUserId(Long userId);
    Optional<UserEducationalExperience> findByUserIdAndEducationalInstitutionId(Long userId, Long institutionId);
}
