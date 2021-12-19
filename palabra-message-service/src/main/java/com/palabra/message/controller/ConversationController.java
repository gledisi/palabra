package com.palabra.message.controller;

import com.palabra.message.service.ConversationService;
import com.palabra.message.service.dto.Conversation;
import com.palabra.message.service.dto.NewConversationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conversation")
public class ConversationController {

    private final ConversationService service;

    @Autowired
    public ConversationController(ConversationService service) {
        this.service = service;
    }

    @GetMapping(value = {"/{userId}"})
    public ResponseEntity<List<Conversation>> getConversations(@PathVariable Long userId){
        return ResponseEntity.ok(service.getConversations(userId));
    }

    @PostMapping
    public ResponseEntity<String> newConversation(@RequestBody NewConversationMessage textMessage){
        service.insertNewTextMessage(textMessage);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping(value = {"/{conversationId}"})
    public ResponseEntity<String> deleteConversation(@PathVariable Long conversationId){
        service.deleteConversation(conversationId);
        return ResponseEntity.ok("ok");
    }

}
