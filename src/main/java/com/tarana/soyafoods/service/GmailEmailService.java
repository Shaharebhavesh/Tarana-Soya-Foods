package com.tarana.soyafoods.service;

import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class GmailEmailService {

    public void sendHtmlEmail(String to, String subject, String html) throws Exception {

        String rawEmail =
                "To: " + to + "\r\n" +
                        "Subject: " + subject + "\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n\r\n" +
                        html;

        String encodedEmail = Base64.getUrlEncoder()
                .encodeToString(rawEmail.getBytes(StandardCharsets.UTF_8));

        Message message = new Message();
        message.setRaw(encodedEmail);

        GmailServiceUtil.getGmailService()
                .users()
                .messages()
                .send("me", message)
                .execute();
    }
}


