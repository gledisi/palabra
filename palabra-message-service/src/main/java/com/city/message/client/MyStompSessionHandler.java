package com.city.message.client;

import com.city.message.entity.MessagesByConversationEntity;
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
    private StompSession session;
    private final String userId;

    public MyStompSessionHandler(String userId) {
        this.userId = userId;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/messages", this);
        String dest = String.format("/user/%s/queue/messages",userId);
        session.subscribe(dest, this);
        log.info("Subscribed to /topic/messages");
        this.session=session;
        log.info("Message sent to websocket server");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg,String toUser){
        NewTextMessage textMessage = getSampleMessage(msg,toUser);
        session.send("/app/newMessage1",textMessage);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessagesByConversationEntity.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessagesByConversationEntity msg = (MessagesByConversationEntity) payload;
       System.out.println(String.format("%s : %s",msg.getFromUser(), msg.getText()));
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private NewTextMessage getSampleMessage(String m,String toUser) {
        NewTextMessage msg = new NewTextMessage();
        msg.setConversationId("59c3ab91-5ce3-11ec-9468-0f77570dcbdb");
        msg.setFromUser(userId);
        msg.setToUser(toUser);
        msg.setText(m);
        return msg;
    }
}
