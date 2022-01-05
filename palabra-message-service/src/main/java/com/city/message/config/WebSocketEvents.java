package com.city.message.config;

import com.city.message.repository.UserActiveStatusRepository;
import com.city.user.security.configuration.PalabraJwtAuthentication;
import com.city.user.security.model.PalabraTokenDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;

@Component
@Slf4j
public class WebSocketEvents {

    private final UserActiveStatusRepository repository;

    @Autowired
    public WebSocketEvents(UserActiveStatusRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        PalabraJwtAuthentication user = (PalabraJwtAuthentication) headers.getUser();
        PalabraTokenDetails details = (PalabraTokenDetails)user.getPrincipal();
        UUID userId = UUID.fromString(details.getUserId());
        log.info("New seesion conected event {}",event.getMessage());
        repository.active(userId);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        log.info("New seesion disconnected event {}",event.getMessage());
        PalabraJwtAuthentication user = (PalabraJwtAuthentication) headers.getUser();
        PalabraTokenDetails details = (PalabraTokenDetails)user.getPrincipal();
        UUID userId = UUID.fromString(details.getUserId());
        repository.unActive(userId);
    }
}
