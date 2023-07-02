package com.licenta.notificationmicroservice.business.service;

import com.licenta.notificationmicroservice.business.interfaces.INotificationService;
import com.licenta.notificationmicroservice.business.model.NotificationDTO;
import com.licenta.notificationmicroservice.business.util.exception.NotFoundException;
import com.licenta.notificationmicroservice.business.util.mapper.NotificationMapper;
import com.licenta.notificationmicroservice.persistence.entity.Notification;
import com.licenta.notificationmicroservice.persistence.repository.NotificationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);

    public static final String NOTIFICATION_NOT_FOUND_MESSAGE = "Notification with id <%d> does not exist.";

    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.toEntity(notificationDTO);

        return notificationMapper.toDTO(notificationRepository.save(notification));
    }

    @Override
    public void updateIsRead(Long userId, Long notificationId, Boolean isRead) {
        Notification notification = getOrElseThrowException(userId, notificationId);

        notification.setIsRead(isRead);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getAllByUserId(Long userId) {
        return notificationRepository.findAllByUserId(userId).stream()
                .map(notificationMapper::toDTO)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Long notificationId) {
        Notification notification = getOrElseThrowException(userId, notificationId);

        notificationRepository.delete(notification);
    }

    private Notification getOrElseThrowException(Long userId, Long notificationId) {

        return notificationRepository.findByUserIdAndId(userId, notificationId).orElseThrow(
                () -> new NotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, notificationId))
        );
    }
}
