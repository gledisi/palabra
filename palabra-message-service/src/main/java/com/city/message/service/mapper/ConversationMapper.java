package com.city.message.service.mapper;

import com.city.message.entity.ConversationMessageEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.service.dto.Conversation;
import com.city.message.service.dto.TextMessage;

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
