package com.city.message.controller;

import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.service.ConversationService;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    private final ConversationService service;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(ConversationService service, SimpMessagingTemplate simpMessagingTemplate) {
        this.service = service;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/newMessage2")
    @SendTo("/topic/messages")
    public MessagesByConversationEntity newMessage(NewTextMessage newTextMessage) {
        return service.newMessage(newTextMessage);
    }

    @MessageMapping("/newMessage1")
    public void newMessage1(NewTextMessage newTextMessage) {
        log.info("NewMessage1 From queue: {}",newTextMessage);
        MessagesByConversationEntity res= service.newMessage(newTextMessage);
        simpMessagingTemplate.convertAndSendToUser(newTextMessage.getToUser().toString(), Constants.USER_MESSAGE_DESTINATION,res);
    }

}
