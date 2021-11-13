package com.city.message.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TextMessage {
    private Long messageId;
    private Long fromUser;
    private LocalDateTime deliveredTime;
    private String text;
}
