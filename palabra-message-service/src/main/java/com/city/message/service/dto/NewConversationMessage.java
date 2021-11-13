package com.city.message.service.dto;

import lombok.Data;

@Data
public class NewConversationMessage {
    private Long fromUser;
    private Long toUser;
    private String text;
}
