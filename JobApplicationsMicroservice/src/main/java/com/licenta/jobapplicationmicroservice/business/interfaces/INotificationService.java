package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.Notification;

public interface INotificationService {

    void sendNotification(Notification notification);
}
