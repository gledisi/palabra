package com.city.message.controller;

import com.city.message.entity.ConversationEntity;
import com.city.message.service.MessageService;
import com.city.message.service.dto.Conversation;
import com.city.message.service.dto.ConversationMessages;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessageController {

    private final MessageService service;

    @Autowired
    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping(value = {"/{userId}"})
    public ResponseEntity<List<Conversation>> getConversations(@PathVariable Long userId){
        return ResponseEntity.ok(service.getConversations(userId));
    }

    @GetMapping(value = {"/{userId}/{conversationId}"})
    public ResponseEntity<ConversationMessages> getConversation(@PathVariable Long userId, @PathVariable Long conversationId){
        return ResponseEntity.ok(service.getConversation(userId,conversationId));
    }

    @PostMapping("/new")
    public ResponseEntity<String> newConversation(@RequestBody NewConversationMessage textMessage){
        service.insertNewTextMessage(textMessage);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping(value = {"/{conversationId}"})
    public ResponseEntity<Boolean> deleteConversation(@PathVariable Long conversationId){
        return ResponseEntity.ok(service.deleteConversation(conversationId));
    }

    @PostMapping
    public ResponseEntity<String> newMessage(@RequestBody NewTextMessage newTextMessage){
        service.insertTextMessage(newTextMessage);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/{messageId}")
    public ResponseEntity<String> replyMessage(@PathVariable Long messageId,@RequestBody NewTextMessage newTextMessage){
        service.replyTextMessage(messageId, newTextMessage);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/{messageId}")
    public ResponseEntity<String> forwardMessage(@PathVariable Long messageId,@RequestBody NewTextMessage newTextMessage){
        service.forwardTextMessage(messageId, newTextMessage);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId){
        service.deleteMessage(messageId);
        return ResponseEntity.ok("OK");
    }

}
