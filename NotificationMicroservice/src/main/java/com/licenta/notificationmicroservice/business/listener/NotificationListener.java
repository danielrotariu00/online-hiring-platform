package com.licenta.notificationmicroservice.business.listener;

import com.licenta.notificationmicroservice.business.constants.KafkaConstants;
import com.licenta.notificationmicroservice.business.interfaces.INotificationService;
import com.licenta.notificationmicroservice.business.model.NotificationDTO;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    private INotificationService notificationService;

    @KafkaListener(
            topics = KafkaConstants.NOTIFICATION_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(NotificationDTO notificationDTO) {
        notificationDTO.setIsRead(false);
        notificationService.save(notificationDTO);
        // template.convertAndSend("/topic/group", message);
    }
}