package com.city.message.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConversationMessages {
    private Long conversationId;
    private Long userId;
    private Long contactName;
    private Byte[] contactPhoto;
    private List<TextMessage> messages;
}
