package com.city.message.service.mapper;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.entity.UnreadMessagesByConversationEntity;
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

    public static ConversationsByUserEntity toEntity(UUID convId,String textMessage){
        ConversationsByUserEntity entity = new ConversationsByUserEntity();
        entity.setConversationId(convId);
        entity.setLastMsg(textMessage);
        entity.setLastMsgTime(LocalDateTime.now());
        return entity;
    }
    public static MessagesByConversationEntity toEntity(NewTextMessage newTextMessage){
        MessagesByConversationEntity entity = new MessagesByConversationEntity();
        entity.setFromUser(newTextMessage.getFromUser());
        entity.setMessageId(UUID.randomUUID());
        entity.setText(newTextMessage.getText());
        entity.setDeliveredTime(LocalDateTime.now());
        entity.setConversationId(newTextMessage.getConversationId());
        return entity;
    }

    public static UnreadMessagesByConversationEntity toUnreadMessage(NewTextMessage newTextMessage){
        UnreadMessagesByConversationEntity entity = new UnreadMessagesByConversationEntity();
        entity.setFromUser(newTextMessage.getFromUser());
        entity.setMessageId(UUID.randomUUID());
        entity.setText(newTextMessage.getText());
        entity.setDeliveredTime(LocalDateTime.now());
        entity.setConversationId(newTextMessage.getConversationId());
        return entity;
    }

    public static UnreadMessagesByConversationEntity toUnreadMessage(NewConversationMessage newTextMessage, UUID conversationId) {
        UnreadMessagesByConversationEntity entity = new UnreadMessagesByConversationEntity();
        entity.setFromUser(UUID.fromString(newTextMessage.getFromUser()));
        entity.setMessageId(UUID.randomUUID());
        entity.setText(newTextMessage.getText());
        entity.setDeliveredTime(LocalDateTime.now());
        entity.setConversationId(conversationId);
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

    public static MessagesByConversationEntity toReadMessage(UnreadMessagesByConversationEntity msg,LocalDateTime readTime) {
        MessagesByConversationEntity entity = new MessagesByConversationEntity();
        entity.setMessageId(msg.getMessageId());
        entity.setText(msg.getText());
        entity.setDeliveredTime(msg.getDeliveredTime());
        entity.setConversationId(msg.getConversationId());
        entity.setUrl(msg.getUrl());
        entity.setFromUser(msg.getFromUser());
        entity.setReadTime(readTime);
        return entity;
    }

    public static List<MessagesByConversationEntity> toReadMessage(List<UnreadMessagesByConversationEntity> msg,LocalDateTime readTime) {
        return  msg.stream().map(m->toReadMessage(m,readTime)).collect(Collectors.toList());
    }

}



















