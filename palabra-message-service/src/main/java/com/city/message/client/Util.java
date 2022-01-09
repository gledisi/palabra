package com.city.message.client;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class Util {
    private static final String URL = "ws://localhost:8081/palabra/socket";
    public static MyStompSessionHandler connect(String userId,String token){
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders(headers(token));
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        MyStompSessionHandler sessionHandler = new MyStompSessionHandler(userId);
        stompClient.connect(URL,headers, sessionHandler);
        return sessionHandler;
    }

    private static HttpHeaders headers(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add(WebSocketHttpHeaders.AUTHORIZATION,token);
        return headers;
    }
}
