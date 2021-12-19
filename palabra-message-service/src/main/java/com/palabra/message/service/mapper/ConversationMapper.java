package com.palabra.message.service.mapper;

import com.palabra.message.entity.UserConversationEntity;
import com.palabra.message.service.dto.Conversation;

import java.util.List;
import java.util.stream.Collectors;

public class ConversationMapper {

    public static List<Conversation> toDto(List<UserConversationEntity> entity){
        return entity.stream().map(ConversationMapper::toDto).collect(Collectors.toList());
    }

    public static Conversation toDto(UserConversationEntity entity){
        Conversation c = new Conversation();
        c.setConversationId(entity.getId());
        return c;
    }
}
