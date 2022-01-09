package com.city.message.service;

import com.city.message.entity.*;
import com.city.message.repository.*;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.ReplyMessage;
import com.city.message.service.dto.UserDetails;
import com.city.message.service.mapper.ConversationMapper;
import com.city.message.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ConversationService(ConversationRepository conversationsByUserRepository, MessageRepository messageRepository, UserServiceAdapter userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.conversationRepository = conversationsByUserRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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
        String text = textMessage.getText();
        UUID convId= UUID.randomUUID();
        ConversationsByUserEntity from = toConversation(convId,text,textMessage.getFromUser(),textMessage.getToUser());
        ConversationsByUserEntity to = toConversation(convId,text,textMessage.getToUser(),textMessage.getFromUser());
        to.setUnreadMsgCount(1);
        conversationRepository.insert(from,to);
        insertNewTextMessage(textMessage,from.getConversationId());
        return from;
    }

    public MessagesByConversationEntity insertNewTextMessage(NewConversationMessage textMessage,UUID convId){
        UnreadMessagesByConversationEntity message = ConversationMapper.toUnreadMessage(textMessage,convId);
        MessagesByConversationEntity messageSent= ConversationMapper.toReadMessage(messageRepository.insertUnreadMessage(message),null);
        simpMessagingTemplate.convertAndSendToUser(textMessage.getToUser(), Constants.USER_MESSAGE_DESTINATION,messageSent);
        return messageSent;
    }

    private ConversationsByUserEntity toConversation(UUID convId,String text,String fromUser,String toUser){
        ConversationsByUserEntity entity = ConversationMapper.toEntity(convId,text);
        entity.setUserId(UUID.fromString(fromUser));
        UserDetails contact = userService.getContactDetails(fromUser,toUser);
        if(contact!=null) {
            entity.setContact(contact);
        }else {
            entity.setContact(userService.getUserDetails(toUser));
        }
       return entity;
    }

    public MessagesByConversationEntity newMessage(NewTextMessage newTextMessage) {
        UnreadMessagesByConversationEntity message = ConversationMapper.toUnreadMessage(newTextMessage);
        conversationRepository.updateUnreadCounter(newTextMessage.getConversationId(),newTextMessage.getToUser());
        conversationRepository.updateConversationByUser(newTextMessage,newTextMessage.getFromUser());
        conversationRepository.updateConversationByUserAndCounter(newTextMessage,newTextMessage.getToUser());
        return ConversationMapper.toReadMessage(messageRepository.insertUnreadMessage(message),null);
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
        List<MessagesByConversationEntity> messages = messageRepository.getMessages(userConversation.getConversationId());
        if(messages.isEmpty() && userConversation.getConversationId()!=null){
            messages.add(new MessagesByConversationEntity(userConversation.getConversationId()));
        }
        return messages;
    }

    public List<MessagesByConversationEntity> getMessagesByConversation(String conversationId) {
        return messageRepository.getMessages(UUID.fromString(conversationId));
    }

    public void readConversation(String cId){
        UUID convId = UUID.fromString(cId);
        UUID uId =userService.getAuthenticatedUser().getUserUUID();
        messageRepository.insertReadMessages(convId);
        messageRepository.deleteUnreadMessaged(convId);
        int msgCount = conversationRepository.resetUnreadMsgCounter(uId,convId);
        conversationRepository.decrementCounter(convId,uId,msgCount);

    }

}
