package com.example.shop.user.common.mail;

import org.springframework.stereotype.Service;

@Service
public class WebServiceEmailService implements EmailSender {

    @Override
    public void send(String receiver, String subject, String text) {
        throw new RuntimeException("Not implemented yet");
    }
}
