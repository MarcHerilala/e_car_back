package com.codinftitans.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender emailSender;

    public String sendMessage (String to , String subject , String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carmarket.0806@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        return "success"
                ;    }

}
