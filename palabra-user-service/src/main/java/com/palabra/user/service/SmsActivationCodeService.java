package com.palabra.user.service;

import com.palabra.palabra.config.MobileProperties;
import com.palabra.user.exceptions.ActivationCodeNotSendException;
import com.twilio.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsActivationCodeService implements ActivationCodeService {


    @Autowired
    private MobileProperties properties;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String send(String to) {
        try {
            return sendSms(to);
        } catch (ApiException e) {
            throw new ActivationCodeNotSendException(e.getMoreInfo());
        }
    }

    private String sendSms(String to) {
        String code = generateCode();
//        Twilio.init(properties.getSid(), properties.getToken());
//        String bodyMessage = properties.getSmsText() + code;
//        Message message = Message.creator(
//                        new PhoneNumber(to),
//                        new PhoneNumber(properties.getNumber()),
//                        bodyMessage)
//                .create();
        log.info("@@@@@@-CODE-@@@@@: {}" ,code);
        return encoder.encode(code);
    }

}
