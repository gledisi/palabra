package com.city.message.service.mapper;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.service.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConversationMapper {

    public static ConversationsByUserEntity toEntity(NewConversationMessage textMessage){
        ConversationsByUserEntity entity = new ConversationsByUserEntity();
        entity.setConversationId(UUID.randomUUID());
        entity.setLastMsg(textMessage.getText());
        entity.setLastMsgTime(LocalDateTime.now());
        return entity;
    }
    public static MessagesByConversationEntity toEntity(NewTextMessage newTextMessage){
        MessagesByConversationEntity entity = new MessagesByConversationEntity();
        entity.setFromUser(newTextMessage.getFromUser());
        entity.setMessageId(newTextMessage.getConversationId());
        entity.setText(newTextMessage.getText());
        entity.setDeliveredTime(LocalDateTime.now());
        entity.setConversationId(newTextMessage.getConversationId());
        return entity;
    }

    public static MessagesByConversationEntity toEntity(ReplyMessage newTextMessage){
        MessagesByConversationEntity entity = new MessagesByConversationEntity();
        entity.setMessageId(UUID.randomUUID());
        entity.setText(newTextMessage.getText());
        entity.setDeliveredTime(LocalDateTime.now());
        entity.setConversationId(UUID.fromString(newTextMessage.getConversationId()));
        return entity;
    }

    public static ConversationsByUserEntity toConversationEntity(MessagesByConversationEntity newTextMessage) {
        ConversationsByUserEntity entity = new ConversationsByUserEntity();
        entity.setLastMsgTime(newTextMessage.getDeliveredTime());
        entity.setLastMsg(newTextMessage.getText());
        entity.setConversationId(newTextMessage.getConversationId());
        return entity;
    }
}



















