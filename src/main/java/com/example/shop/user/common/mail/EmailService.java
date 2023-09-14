package com.example.shop.user.common.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService implements EmailSender {

    public static final String BEAN_NAME = "emailService";

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(String receiver, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Shop <shop@shop.com>");
        message.setReplyTo("Shop <shop@shop.com>");
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        log.info("Email sent");
    }
}
