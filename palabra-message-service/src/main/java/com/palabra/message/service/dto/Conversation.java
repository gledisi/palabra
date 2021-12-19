package com.palabra.message.service.dto;

import lombok.Data;

@Data
public class Conversation {
    private Long conversationId;
    private Long userId;
    private Long contactName;
    private Byte[] contactPhoto;
    private TextMessage lastMessage;
    private Integer unreadMessages;
}
