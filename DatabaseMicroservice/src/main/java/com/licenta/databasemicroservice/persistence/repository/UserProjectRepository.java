package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    List<UserProject> findAllByUserId(Long userId);
    Optional<UserProject> findByUserIdAndId(Long userId, Long Id);
}
