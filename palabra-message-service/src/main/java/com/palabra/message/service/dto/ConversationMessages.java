package com.palabra.message.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConversationMessages {
    private Long conversationId;
    private UserDetails userDetails;
    private List<TextMessage> messages;
}
