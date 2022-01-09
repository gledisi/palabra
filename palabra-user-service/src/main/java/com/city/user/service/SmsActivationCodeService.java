package com.city.user.service;

import com.city.palabra.config.MobileProperties;
import com.city.user.exceptions.ActivationCodeNotSendException;
import com.twilio.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Service
@Slf4j
public class SmsActivationCodeService implements ActivationCodeService {


    @Autowired
    private MobileProperties properties;

    @Autowired
    @Lazy
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
        Twilio.init(properties.getSid(), properties.getToken());
        String bodyMessage = properties.getSmsText() + code;
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(properties.getNumber()),
                        bodyMessage)
                .create();
        log.info("@@@@@@-CODE-@@@@@: {}" ,code);
        return encoder.encode(code);
    }

}
