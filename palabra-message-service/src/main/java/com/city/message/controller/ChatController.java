package com.city.message.controller;

import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.service.ConversationService;
import com.city.message.service.dto.NewTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ConversationService service;

    @Autowired
    public ChatController(ConversationService service) {
        this.service = service;
    }

    @MessageMapping("/newMessage")
    @SendTo("/topic/messages")
    public MessagesByConversationEntity newMessage(NewTextMessage newTextMessage) {
        return service.newMessage(newTextMessage);
    }

}
