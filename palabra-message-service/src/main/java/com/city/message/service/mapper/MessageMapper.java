package com.city.message.service.mapper;

import com.city.message.entity.ConversationMessageEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.service.dto.ConversationMessages;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.TextMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {

    public static UserConversationEntity toEntity(NewConversationMessage textMessage){
        UserConversationEntity entity = new UserConversationEntity();
        //entity.setUserConversationKey(new UserConversationKey(textMessage.getFromUser(),textMessage.getToUser()));
        return entity;
    }
    public static ConversationMessageEntity toEntity(NewTextMessage newTextMessage){
        ConversationMessageEntity entity = new ConversationMessageEntity();
        entity.setText(newTextMessage.getText());
        entity.setDeliveredTime(LocalDateTime.now());
        entity.setConversationId(newTextMessage.getConversationId());
        return entity;
    }

    public static TextMessage toDTO(ConversationMessageEntity entity) {
        TextMessage message = new TextMessage();
        message.setMessageId(entity.getId());
        message.setText(entity.getText());
        message.setDeliveredTime(entity.getDeliveredTime());
        message.setFromUser(entity.getFromUser());
        return message;
    }

    public static List<TextMessage> toDTO(List<ConversationMessageEntity> entity){
        return entity.stream().map(MessageMapper::toDTO).collect(Collectors.toList());
    }

    public static TextMessage toResponse(NewTextMessage msg) {
        return null;
    }
}



















