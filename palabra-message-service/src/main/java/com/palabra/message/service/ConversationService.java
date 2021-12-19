package com.palabra.message.service;

import com.palabra.message.entity.UserConversationEntity;
import com.palabra.message.repository.UserConversationRepository;
import com.palabra.message.service.dto.Conversation;
import com.palabra.message.service.dto.NewConversationMessage;
import com.palabra.message.service.mapper.ConversationMapper;
import com.palabra.message.service.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {
    private static final String DECRYPTION_KEY = "simple_test";

    private final UserConversationRepository repository;

    @Autowired
    public ConversationService(UserConversationRepository repository) {
        this.repository = repository;
    }


    public List<Conversation> getConversations(Long userId) {
        List<UserConversationEntity> entities = repository.findByUser1IdOrUser2Id(userId,userId);
        return ConversationMapper.toDto(entities);
    }
    public void insertNewTextMessage(NewConversationMessage textMessage){
        UserConversationEntity userConversationEntity = MessageMapper.toEntity(textMessage);
        userConversationEntity.setDecryptionKey(DECRYPTION_KEY);
        repository.insert(userConversationEntity);
    }

    public void deleteConversation(Long conversationId) {
        repository.deleteById(conversationId);
    }
}
