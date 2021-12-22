package com.city.message.service.dto;

import lombok.Data;

@Data
public class NewConversationMessage {
    private String toUser;
    private String text;
}
