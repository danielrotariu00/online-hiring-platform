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
    public void save(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.toEntity(notificationDTO);

        notificationRepository.save(notification);
    }

    @Override
    public void updateIsRead(Long notificationId, Boolean isRead) {
        Notification notification = getOrElseThrowException(notificationId);

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
    public void delete(Long notificationId) {
        getOrElseThrowException(notificationId);

        notificationRepository.deleteById(notificationId);
    }

    private Notification getOrElseThrowException(Long notificationId) {

        return notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, notificationId))
        );
    }
}
