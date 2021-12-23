package com.city.message.client;

import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;
@Slf4j
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/messages", this);
        log.info("Subscribed to /topic/messages");
        session.send("/app/newMessage", getSampleMessage());
        log.info("Message sent to websocket server");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return TextMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        TextMessage msg = (TextMessage) payload;
        log.info("Received : " + msg.getText() + " from : " + msg.getFromUser());
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private NewTextMessage getSampleMessage() {
        NewTextMessage msg = new NewTextMessage();
        msg.setConversationId("59c3ab91-5ce3-11ec-9468-0f77570dcbdb");
        msg.setText("Howdy!!");
        return msg;
    }
}
