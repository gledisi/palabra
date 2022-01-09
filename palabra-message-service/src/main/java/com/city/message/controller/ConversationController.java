package com.city.message.controller;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.MessagesByConversationEntity;
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

    @GetMapping
    public ResponseEntity<List<ConversationsByUserEntity>> getConversationsByUser() {
        return ResponseEntity.ok(service.getConversationsByUser());
    }

    @PostMapping
    public ResponseEntity<ConversationsByUserEntity> newConversation(@RequestBody NewConversationMessage textMessage) {
        return ResponseEntity.ok(service.insertNewTextMessage(textMessage));
    }

    @PutMapping("/read")
    public ResponseEntity<String> readConversation(@RequestParam("conversationId") String conversationId) {
        service.readConversation(conversationId);
        return ResponseEntity.ok("Ok");
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
