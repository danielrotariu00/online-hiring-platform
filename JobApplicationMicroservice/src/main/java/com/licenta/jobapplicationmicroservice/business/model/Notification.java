package com.licenta.jobapplicationmicroservice.business.model;

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

    private Long userId;
    private Long jobApplicationId;
    private NotificationType type;
    private String timestamp;
}
