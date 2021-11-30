package com.city.message.service;

import com.city.message.entity.ConversationMessageEntity;
import com.city.message.repository.ConversationMessageRepository;
import com.city.message.service.dto.*;
import com.city.message.service.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private static final Long LOGGEDIN_USER = 1L;
    private final ConversationMessageRepository repository;

    @Autowired
    public MessageService(ConversationMessageRepository conversationRepository) {
        this.repository = conversationRepository;
    }

    public TextMessage insertTextMessage(NewTextMessage newTextMessage){
        ConversationMessageEntity entity= MessageMapper.toEntity(newTextMessage);
        entity.setId(uniqueId());
        entity.setFromUser(LOGGEDIN_USER);
        return MessageMapper.toDTO(repository.insert(entity));
    }

    private Long uniqueId(){
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public ConversationMessages getConversation(Long id) {
        List<ConversationMessageEntity> entities = repository.findByConversationId(id);

        ConversationMessages conversation = new ConversationMessages();
        conversation.setConversationId(id);
        conversation.setUserDetails(null);
        conversation.setMessages(MessageMapper.toDTO(entities));
        return conversation;
    }

    public void replyTextMessage(Long messageId, NewTextMessage newTextMessage) {
        repository.insert(MessageMapper.toEntity(newTextMessage));
    }

    public void forwardTextMessage(Long messageId, NewTextMessage newTextMessage) {
        repository.insert(MessageMapper.toEntity(newTextMessage));
    }

    public void deleteMessage(Long messageId) {
        repository.deleteById(messageId);
    }


}
