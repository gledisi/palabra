package com.city.message.service.dto;

import lombok.Data;

@Data
public class NewTextMessage {
    private String conversationId;
    private String text;
}
