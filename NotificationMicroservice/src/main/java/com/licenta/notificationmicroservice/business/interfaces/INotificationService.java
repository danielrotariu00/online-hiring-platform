package com.licenta.notificationmicroservice.business.interfaces;

import com.licenta.notificationmicroservice.business.model.NotificationDTO;

import java.util.List;

public interface INotificationService {
    NotificationDTO save(NotificationDTO notificationDTO);
    void updateIsRead(Long notificationId, Boolean isRead);
    List<NotificationDTO> getAllByUserId(Long userId);
    void delete(Long notificationId);
}
