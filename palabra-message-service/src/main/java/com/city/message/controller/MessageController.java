package com.city.message.controller;

import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.entity.UserActiveStatusEntity;
import com.city.message.repository.UserActiveStatusRepository;
import com.city.message.service.ConversationService;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.ReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("messages")
public class MessageController {

    private final ConversationService service;

    private final UserActiveStatusRepository repository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageController(ConversationService service, UserActiveStatusRepository repository, SimpMessagingTemplate simpMessagingTemplate) {
        this.service = service;
        this.repository = repository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping(value = {"/activeUser"})
    public ResponseEntity<UserActiveStatusEntity> getUserActiveStatus(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(repository.getStatus(UUID.fromString(userId)));
    }

    @GetMapping(value = {"/{conversationId}"})
    public ResponseEntity<List<MessagesByConversationEntity>> getConversationMessages(@PathVariable String conversationId) {
        return ResponseEntity.ok(service.getMessagesByConversation(conversationId));
    }

    @GetMapping
    public ResponseEntity<List<MessagesByConversationEntity>> getConversationMessagesByUser(@PathParam("userId") String userId) {
        return ResponseEntity.ok(service.getMessagesByUser(userId));
    }

    @MessageMapping("/newMessage")
    public void newMessage(NewTextMessage newTextMessage) {
        log.info("NewMessage : {}",newTextMessage);
        MessagesByConversationEntity res= service.newMessage(newTextMessage);
        simpMessagingTemplate.convertAndSendToUser(newTextMessage.getToUserString(),"/queue/messages",res);
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
