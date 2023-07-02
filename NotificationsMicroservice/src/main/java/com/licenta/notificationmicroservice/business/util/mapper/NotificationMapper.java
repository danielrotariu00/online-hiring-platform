package com.licenta.notificationmicroservice.business.util.mapper;

import com.licenta.notificationmicroservice.business.model.NotificationDTO;
import com.licenta.notificationmicroservice.persistence.entity.Notification;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {

    Notification toEntity(NotificationDTO notificationDTO);
    NotificationDTO toDTO(Notification notification);
}
