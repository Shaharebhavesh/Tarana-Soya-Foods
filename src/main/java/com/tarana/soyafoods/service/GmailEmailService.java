package com.tarana.soyafoods.service;

import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class GmailEmailService {

    public void sendHtmlEmail(String to, String subject, String html) throws Exception {

        // 🔐 Basic validation
        if (to == null || !to.contains("@")) {
            throw new IllegalArgumentException("Invalid recipient email: " + to);
        }

        String plainText =
                "\uD83C\uDF31 Thank you for contacting Tarana Soya Foods!\n\n" +
                        "We have received your message and will get back to you shortly.\n\n" +
                        "— Tarana Soya Foods";

        String rawEmail =
                "From: Tarana Soya Foods <bhavesh.shahare05@gmail.com>\r\n" +
                        "To: " + to.trim() + "\r\n" +
                        "Reply-To: bhavesh.shahare05@gmail.com\r\n" +
                        "Subject: " + subject + "\r\n" +
                        "MIME-Version: 1.0\r\n" +
                        "Content-Type: multipart/alternative; boundary=\"boundary\"\r\n\r\n" +

                        "--boundary\r\n" +
                        "Content-Type: text/plain; charset=UTF-8\r\n\r\n" +
                        plainText + "\r\n\r\n" +

                        "--boundary\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n\r\n" +
                        html + "\r\n\r\n" +

                        "--boundary--";

        String encodedEmail = Base64.getUrlEncoder()
                .encodeToString(rawEmail.getBytes(StandardCharsets.UTF_8));

        Message message = new Message();
        message.setRaw(encodedEmail);

        Message sent = GmailServiceUtil.getGmailService()
                .users()
                .messages()
                .send("me", message)
                .execute();

        // 🔍 DEBUG (keep for now)
        System.out.println("USER EMAIL SENT TO: " + to);
        System.out.println("MESSAGE ID: " + sent.getId());
    }
}


