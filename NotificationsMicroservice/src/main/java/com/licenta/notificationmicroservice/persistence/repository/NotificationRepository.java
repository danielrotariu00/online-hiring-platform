package com.licenta.notificationmicroservice.persistence.repository;

import com.licenta.notificationmicroservice.persistence.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);
    Optional<Notification> findByUserIdAndId(Long userId, Long notificationId);
}
