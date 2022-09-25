package com.funix.linhvm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

	 @Autowired
    public JavaMailSender emailSender;

    public String sendSimpleEmail(String email, String code) {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(email);
        message.setSubject("Xin chào bạn đến với Loha Shop");
        message.setText(code);

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
