package com.city.user.service;

import java.util.concurrent.ThreadLocalRandom;

public interface ActivationCodeService {
    String send(String sendTo);

    default String generateCode(){
        int i = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(i);
    }
}
