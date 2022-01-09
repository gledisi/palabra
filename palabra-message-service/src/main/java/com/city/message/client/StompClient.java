package com.city.message.client;

import java.util.Scanner;

public class StompClient {
private static final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkNmQxZTU2My04M2M2LTQwNTItYTY1Yy0xMzU1NTIyOTllZjEiLCJzdWIiOiIxMjMxMjMiLCJpYXQiOjE2NDE1ODE3MDIsImV4cCI6MTY0MTYwMzMwMiwicm9sZXMiOlt7ImlkIjoxLCJhdXRob3JpdHkiOiJVU0VSIn1dLCJ1c2VySWQiOiI0NGI1ZmM0Yi1jYjAxLTQ5YjYtODgyYS01NTg4MGMxZTgyYTgifQ.vGmoou3Eg5oon0_MtAYDbz7zzeZ81P4laxxMOnXbR0E";

    public static void main(String[] args) {
        MyStompSessionHandler sessionHandler = Util.connect("44b5fc4b-cb01-49b6-882a-55880c1e82a8",token);
        while (true) {
            String n = new Scanner(System.in).nextLine();
            if (n != null) {
                sessionHandler.sendMessage(n,"6c4ce2cb-fd02-4eba-a189-a3611d989f82");
            }
        }
    }
}
