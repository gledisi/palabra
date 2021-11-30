package com.city.message.controller;

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



    @GetMapping(value = {"/{conversationId}"})
    public ResponseEntity<ConversationMessages> getConversation(@PathVariable Long conversationId){
        return ResponseEntity.ok(service.getConversation(conversationId));
    }


    @PostMapping
    public ResponseEntity<String> newMessage(@RequestBody NewTextMessage newTextMessage){
        service.insertTextMessage(newTextMessage);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/replay/{messageId}")
    public ResponseEntity<String> replyMessage(@PathVariable Long messageId,@RequestBody NewTextMessage newTextMessage){
        service.replyTextMessage(messageId, newTextMessage);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/forward/{messageId}")
    public ResponseEntity<String> forwardMessage(@PathVariable Long messageId,@RequestBody NewTextMessage newTextMessage){
        service.forwardTextMessage(messageId, newTextMessage);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping(value = "/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId){
        service.deleteMessage(messageId);
        return ResponseEntity.ok("OK");
    }

}
