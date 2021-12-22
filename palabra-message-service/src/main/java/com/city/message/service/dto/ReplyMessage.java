package com.city.message.service.dto;

import lombok.Data;

@Data
public class ReplyMessage {
    private String conversationId;
    private String text;
    private String message;
}
