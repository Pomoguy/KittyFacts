package com.bpmn2.kittyfacts.service;

import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;


@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;


    public void sendEmail(String email,String content) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(from);
        helper.setSubject("Your Kitty Fact");
        helper.setTo(email);
        helper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }

}
