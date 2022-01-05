package com.city.message.service;

import com.city.message.entity.*;
import com.city.message.repository.*;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.ReplyMessage;
import com.city.message.service.dto.UserDetails;
import com.city.message.service.mapper.ConversationMapper;
import com.city.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {
    private static final String DECRYPTION_KEY = "simple_test";
    //TODO: get it from security context;
    private static final UUID USER_ID = UUID.fromString("44b5fc4b-cb01-49b6-882a-55880c1e82a8");

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserServiceAdapter userService;

    @Autowired
    public ConversationService(ConversationRepository conversationsByUserRepository, MessageRepository messageRepository, UserServiceAdapter userService) {
        this.conversationRepository = conversationsByUserRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    //OPERATION RELATED TO CONVERSATION
    public Boolean deleteConversation(String conversationId) {
        return conversationRepository.deleteById(UUID.fromString(conversationId));
    }


    public List<ConversationsByUserEntity> getConversationsByUser() {
        return conversationRepository.findByUserId(userService.getAuthenticatedUser().getUserUUID());
    }

    //OPERATION RELATED TO MESSAGES

    public ConversationsByUserEntity insertNewTextMessage(NewConversationMessage textMessage){
        ConversationsByUserEntity entity = ConversationMapper.toEntity(textMessage);
        entity.setUserId(userService.getAuthenticatedUser().getUserUUID());
        entity.setContact(userService.getUserDetails(textMessage.getToUser()));
        return conversationRepository.insert(entity);
    }

    public MessagesByConversationEntity newMessage(NewTextMessage newTextMessage) {
        MessagesByConversationEntity message = ConversationMapper.toEntity(newTextMessage);
        conversationRepository.updateUnreadCounter(newTextMessage.getConversationId(),newTextMessage.getToUser());
        conversationRepository.updateConversationByUser(newTextMessage,newTextMessage.getFromUser());
        conversationRepository.updateConversationByUser(newTextMessage,newTextMessage.getToUser());
        return messageRepository.insertMessage(message);
    }

    public MessagesByConversationEntity replyTextMessage(ReplyMessage newTextMessage) {
        MessagesByConversationEntity entity = ConversationMapper.toEntity(newTextMessage);
        entity.setFromUser(userService.getAuthenticatedUser().getUserUUID());
        return messageRepository.insertMessage(entity);
    }

    public Boolean deleteMessage(String messageId) {
        return messageRepository.deleteMessage(UUID.fromString(messageId));
    }

    public List<MessagesByConversationEntity> getMessagesByUser(String userId) {
        UserConversationEntity userConversation= conversationRepository.findUserConversation(userService.getAuthenticatedUser().getUserUUID(),UUID.fromString(userId));
        if(userConversation==null || userConversation.getConversationId()==null)return Collections.emptyList();
        return messageRepository.getMessages(userConversation.getConversationId());
    }

    public List<MessagesByConversationEntity> getMessagesByConversation(String conversationId) {
        return messageRepository.getMessages(UUID.fromString(conversationId));
    }


}
