package com.example.shop.user.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailClientService {

    @Value("${app.email.fake.sender}")
    private boolean isFake;

    private final Map<String, EmailSender> senderMap;

    public EmailSender getInstance(String beanName) {
        if (isFake) {
            return senderMap.get("fakeEmailService");
        }
        return senderMap.get(beanName);
    }
}
