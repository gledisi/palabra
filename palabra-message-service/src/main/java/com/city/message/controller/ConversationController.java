package com.city.message.controller;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.service.ConversationService;
import com.city.message.service.dto.NewConversationMessage;
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
//TODO: REMOVE because is not needed and doesn't make sense
//    @GetMapping(value = {"/{conversationId}"})
//    public ResponseEntity<ConversationsByUserEntity> getConversation(@PathVariable String conversationId) {
//        return ResponseEntity.ok(service.getConversation(conversationId));
//    }

    @GetMapping
    public ResponseEntity<List<ConversationsByUserEntity>> getConversationsByUser() {
        return ResponseEntity.ok(service.getConversationsByUser());
    }

    @PostMapping
    public ResponseEntity<ConversationsByUserEntity> newConversation(@RequestBody NewConversationMessage textMessage) {
        return ResponseEntity.ok(service.insertNewTextMessage(textMessage));
    }

    //TODO: Search conversations by user contact name
    @PostMapping(value = "/search")
    public ResponseEntity<ConversationsByUserEntity> search() {
        return null;//ResponseEntity.ok(service.search());
    }

    @DeleteMapping(value = {"/{conversationId}"})
    public ResponseEntity<Boolean> deleteConversation(@PathVariable String conversationId) {
        return ResponseEntity.ok(service.deleteConversation(conversationId));
    }

}
