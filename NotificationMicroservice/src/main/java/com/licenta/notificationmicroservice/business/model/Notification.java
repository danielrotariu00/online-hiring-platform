package com.licenta.notificationmicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String userId;
    private Integer jobApplicationId;
    private NotificationType type;
    private String timestamp;
}