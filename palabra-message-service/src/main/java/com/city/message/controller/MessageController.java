package com.city.message.controller;

import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.service.ConversationService;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.ReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("messages")
public class MessageController {

    private final ConversationService service;

    @Autowired
    public MessageController(ConversationService service) {
        this.service = service;
    }

    @GetMapping(value = {"/{conversationId}"})
    public ResponseEntity<List<MessagesByConversationEntity>> getConversationMessages(@PathVariable String conversationId) {
        return ResponseEntity.ok(service.getMessagesByConversation(conversationId));
    }

    @GetMapping
    public ResponseEntity<List<MessagesByConversationEntity>> getConversationMessagesByUser(@PathParam("userId") String userId) {
        return ResponseEntity.ok(service.getMessagesByUser(userId));
    }

    @MessageMapping("/new")
    @SendTo("/topic/messages")
    public ResponseEntity<MessagesByConversationEntity> newMessage(NewTextMessage newTextMessage) {
        return ResponseEntity.ok(service.newMessage(newTextMessage));
    }

    @MessageMapping("/reply")
    @SendTo("/topic/messages")
    public ResponseEntity<MessagesByConversationEntity> replyMessage(ReplyMessage newTextMessage) {
        return ResponseEntity.ok(service.replyTextMessage(newTextMessage));
    }

    //TODO:
//    @PostMapping(value = "/forward")
//    public ResponseEntity<MessagesByConversationEntity> forwardMessage(ForwardMessage newTextMessage){
//        return ResponseEntity.ok(service.forwardTextMessage(newTextMessage));
//    }

    @DeleteMapping(value = "/{messageId}")
    public ResponseEntity<Boolean> deleteMessage(@PathVariable String messageId) {
        return ResponseEntity.ok(service.deleteMessage(messageId));
    }

}
