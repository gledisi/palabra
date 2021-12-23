package com.city.message.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NewTextMessage {
    private String conversationId;
    private String fromUser;
    private String toUser;
    private String text;

    public UUID getConversationId() {
        return UUID.fromString(conversationId);
    }

    public UUID getFromUser() {
        return UUID.fromString(fromUser);
    }

    public UUID getToUser() {
        return UUID.fromString(toUser);
    }
}
