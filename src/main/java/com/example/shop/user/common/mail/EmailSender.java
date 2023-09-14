package com.example.shop.user.common.mail;

public interface EmailSender {

    void send(String receiver, String subject, String text);
}
