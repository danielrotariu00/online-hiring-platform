package com.licenta.idm.persistence.repository;

import com.licenta.idm.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUserId(Long userId);
}
