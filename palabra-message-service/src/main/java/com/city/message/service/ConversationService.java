package com.city.message.service;

import com.city.message.entity.*;
import com.city.message.repository.*;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.ReplyMessage;
import com.city.message.service.mapper.ConversationMapper;
import com.city.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ConversationsByUserEntity insertNewTextMessage(NewConversationMessage textMessage){
        ConversationsByUserEntity entity = ConversationMapper.toEntity(textMessage);
        entity.setUserId(USER_ID);
        entity.setContact(userService.getUserDetails(textMessage.getToUser()));
        return conversationRepository.insert(entity);
    }

    public Boolean deleteConversation(String conversationId) {
        return conversationRepository.deleteById(UUID.fromString(conversationId));
    }


    public List<ConversationsByUserEntity> getConversationsByUser() {
        return conversationRepository.findByUserId(USER_ID);
    }

    public ConversationsByUserEntity getConversation(String conversationId) {
       return conversationRepository.findByUserIdAndConversationId(USER_ID,UUID.fromString(conversationId));
    }

    //OPERATION RELATED TO MESSAGES

    public List<MessagesByConversationEntity> getMessagesByConversation(String conversationId) {
        return messageRepository.getMessages(UUID.fromString(conversationId));
    }

    public MessagesByConversationEntity newMessage(NewTextMessage newTextMessage) {
        MessagesByConversationEntity entity = ConversationMapper.toEntity(newTextMessage);
        entity.setFromUser(USER_ID);
        return messageRepository.insertMessage(entity);
    }

    public MessagesByConversationEntity replyTextMessage(ReplyMessage newTextMessage) {
        MessagesByConversationEntity entity = ConversationMapper.toEntity(newTextMessage);
        entity.setFromUser(USER_ID);
        return messageRepository.insertMessage(entity);
    }

    public Boolean deleteMessage(String messageId) {
        return messageRepository.deleteMessage(UUID.fromString(messageId));
    }

    public List<MessagesByConversationEntity> getMessagesByUser(String userId) {
        UserConversationEntity userConversation= conversationRepository.findUserConversation(USER_ID,UUID.fromString(userId));
        if(userConversation==null || userConversation.getConversationId()==null)return Collections.emptyList();
        return messageRepository.getMessages(userConversation.getConversationId());
    }
}
