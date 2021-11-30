package com.city.message.controller;

import com.city.message.service.MessageService;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final MessageService service;

    @Autowired
    public ChatController(MessageService service) {
        this.service = service;
    }


    @GetMapping("/ping")
    public ResponseEntity<TextMessage> ping(){
        TextMessage txt = new TextMessage();
        txt.setDeliveredTime(LocalDateTime.now());
        txt.setText("Ping!");
        txt.setMessageId(1L);
        txt.setFromUser(1L);
        return ResponseEntity.ok(txt);
    }

    @MessageMapping("/newMessage")
    @SendTo("/topic/messages")
    public TextMessage newMessage(NewTextMessage newTextMessage){
        return service.insertTextMessage(newTextMessage);
    }
}
