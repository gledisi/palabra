package com.city.message.client;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StompClient {
    private static String URL = "ws://localhost:8081/palabra/socket";

    public static void main(String[] args) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders(headers());
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(URL,headers, sessionHandler);
        new Scanner(System.in).nextLine(); // Don't close immediately.
    }

    private static HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(WebSocketHttpHeaders.AUTHORIZATION,"Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhMjE2Y2Q2ZS1jZDEyLTRiOTctOWVlNS1jMjJiNTM3ODYxNzIiLCJzdWIiOiIxMjMxMjMiLCJpYXQiOjE2NDAyNTU3NTUsImV4cCI6MTY0MDI3NzM1NSwicm9sZXMiOlt7ImlkIjoxLCJhdXRob3JpdHkiOiJVU0VSIn1dfQ.HL0DNX_ADsLpOQwkH2zdi38hEAcV_8uSzeZiHfJfavg");
        return headers;
    }
}
