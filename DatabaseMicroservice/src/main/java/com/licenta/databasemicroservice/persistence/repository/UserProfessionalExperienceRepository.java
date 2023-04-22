package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.UserProfessionalExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfessionalExperienceRepository extends JpaRepository<UserProfessionalExperience, Long> {
    List<UserProfessionalExperience> findAllByUserId(Long userId);
    Optional<UserProfessionalExperience> findByUserIdAndId(Long userId, Long id);
}
