package com.city.message.service.dto;

import lombok.Data;

import java.util.List;

@Data
//TODO: I think this is not necessary. Should do it another way.
public class ConversationMessages {
    private Long conversationId;
    private UserDetails userDetails;
    private List<TextMessage> messages;
}
