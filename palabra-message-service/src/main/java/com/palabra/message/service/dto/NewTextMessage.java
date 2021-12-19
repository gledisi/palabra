package com.palabra.message.service.dto;

import lombok.Data;

@Data
public class NewTextMessage {
    private Long conversationId;
    private String text;
}
