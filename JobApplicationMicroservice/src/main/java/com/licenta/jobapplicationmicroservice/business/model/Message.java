package com.licenta.jobapplicationmicroservice.business.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Message {

    @NotNull
    private Long userId;

    @NotNull
    private String content;

    private LocalDateTime timestamp;
}
