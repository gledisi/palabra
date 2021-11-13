package com.city.message.service.dto;

import lombok.Data;

@Data
public class NewTextMessage {
    private Long fromUser;
    private Long conversationId;
    private String text;
}
