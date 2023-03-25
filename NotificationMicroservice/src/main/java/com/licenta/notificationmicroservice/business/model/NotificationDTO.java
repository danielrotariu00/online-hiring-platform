package com.licenta.notificationmicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO implements Comparable<NotificationDTO> {

    private Long id;
    private Long userId;
    private String jobApplicationId;
    private String text;
    private Boolean isRead;
    private MessageDTO message;
    private LocalDateTime timestamp;

    @Override
    public int compareTo(NotificationDTO notificationDTO) {
        return timestamp.compareTo(notificationDTO.getTimestamp());
    }
}