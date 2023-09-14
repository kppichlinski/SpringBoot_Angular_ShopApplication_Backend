package com.example.shop.user.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FakeEmailService implements EmailSender {

    @Override
    public void send(String receiver, String subject, String text) {
        log.info("Email sent");
        log.info("Receiver: " + receiver);
        log.info("Subject: " + subject);
        log.info("Text: " + text);
    }
}
