package com.licenta.notificationmicroservice.business.listener;

import com.licenta.notificationmicroservice.business.constants.KafkaConstants;
import com.licenta.notificationmicroservice.business.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(
            topics = KafkaConstants.NOTIFICATION_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Notification notification) {
        System.out.println(notification.getUserId());
        System.out.println(notification.getJobApplicationId());
        System.out.println(notification.getType());
        System.out.println(notification.getTimestamp());
        // template.convertAndSend("/topic/group", message);
    }
}