package com.city.message.service;

import com.city.message.entity.ConversationEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.repository.ConversationRepository;
import com.city.message.repository.UserConversationRepository;
import com.city.message.service.dto.Conversation;
import com.city.message.service.dto.ConversationMessages;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.mapping.MessageMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private static final String DECRYPTION_KEY = "simple_test";
    private final UserConversationRepository userConversationRepository;
    private final ConversationRepository conversationRepository;

    public MessageService(UserConversationRepository userConversationRepository, ConversationRepository conversationRepository) {
        this.userConversationRepository = userConversationRepository;
        this.conversationRepository = conversationRepository;
    }

    public void insertNewTextMessage(NewConversationMessage textMessage){
          UserConversationEntity userConversationEntity = MessageMapping.toEntity(textMessage);
          userConversationEntity.setDecryptionKey(DECRYPTION_KEY);
          userConversationRepository.insert(userConversationEntity);
    }

    public void insertTextMessage(NewTextMessage newTextMessage){
        ConversationEntity entity=MessageMapping.toEntity(newTextMessage);
        conversationRepository.insert(entity);
    }

    public List<Conversation> getConversations(Long userId) {
        return conversationRepository.findByUserId(userId);
    }

    public ConversationMessages getConversation(Long userId, Long conversationId) {
        conversationRepository.findByConversationId(conversationId);
    }

    public Boolean deleteConversation(Long conversationId) {
        return userConversationRepository.deleteById(conversationId);
    }

    public void replyTextMessage(Long messageId, NewTextMessage newTextMessage) {
        conversationRepository.insert(MessageMapping.toEntity(newTextMessage));
    }

    public void forwardTextMessage(Long messageId, NewTextMessage newTextMessage) {
        conversationRepository.insert(MessageMapping.toEntity(newTextMessage));
    }

    public void deleteMessage(Long messageId) {
        conversationRepository.deleteById(messageId);
    }
}
