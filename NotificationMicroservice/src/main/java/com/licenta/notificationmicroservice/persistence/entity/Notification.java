package com.licenta.notificationmicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notification")
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false)
    private Long jobApplicationId;

    @Column(nullable=false)
    private String text;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable=false)
    private LocalDateTime timestamp;
}
