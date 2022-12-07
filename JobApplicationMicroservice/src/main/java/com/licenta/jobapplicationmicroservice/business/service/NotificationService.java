package com.licenta.jobapplicationmicroservice.business.service;

import com.licenta.jobapplicationmicroservice.business.interfaces.INotificationService;
import com.licenta.jobapplicationmicroservice.business.model.Notification;
import com.licenta.jobapplicationmicroservice.business.util.constants.KafkaConstants;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    @Override
    @SneakyThrows
    public void sendNotification(Notification notification) {
        kafkaTemplate.send(KafkaConstants.NOTIFICATION_TOPIC, notification).get();
    }
}
