package com.tarana.soyafoods.controller;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/contact/send")
    public String sendContactMail(@RequestParam("name") String name,
                                  @RequestParam("email") String email,
                                  @RequestParam("message") String message,
                                  RedirectAttributes redirectAttributes) {
        try {
            /* ========== 1️⃣  Send Inquiry to Tarana team ========== */
            MimeMessage adminMsg = mailSender.createMimeMessage();
            MimeMessageHelper adminHelper = new MimeMessageHelper(adminMsg, true, "UTF-8");

            adminHelper.setFrom("bhavesh.shahare05@gmail.com", "Tarana Soya Foods");
            adminHelper.setTo("bhavesh.shahare05@gmail.com");
            adminHelper.setReplyTo(email);
            adminHelper.setSubject("📩 New Contact Form Message from " + name);

            String adminHtml = """
                <div style="font-family:'Poppins',sans-serif;background-color:#f7fff9;padding:25px;">
                    <div style="max-width:600px;margin:auto;background:white;border-radius:12px;box-shadow:0 3px 10px rgba(0,0,0,0.1);">
                        <div style="text-align:center;background-color:#2b7a3d;padding:20px 0;border-top-left-radius:12px;border-top-right-radius:12px;">
                                <img src="https://lh3.googleusercontent.com/p/AF1QipOJnrOsvCQ2kuQ3-vADJaLeYOPM_jL_beQNbMaS=s1360-w1360-h1020-rw"
                                  width="120"
                                  alt="Tarana Soya Foods Logo"> 
                            <h2 style="color:white;margin:0;">Tarana Soya Foods</h2>
                        </div>
                        <div style="padding:25px;color:#333;">
                            <h3 style="color:#2b7a3d;">New Inquiry Received</h3>
                            <p><strong>Name:</strong> %s</p>
                            <p><strong>Email:</strong> %s</p>
                            <p><strong>Message:</strong></p>
                            <div style="background:#f1fdf5;padding:15px;border-left:4px solid #2b7a3d;border-radius:6px;">%s</div>
                            <p style="margin-top:25px;font-size:14px;color:#666;">You can reply directly to this email to respond.</p>
                        </div>
                        <div style="background:#f8f8f8;padding:15px;text-align:center;border-bottom-left-radius:12px;border-bottom-right-radius:12px;font-size:13px;color:#555;">
                            &copy; 2025 <strong>Tarana Soya Foods</strong>, Nagpur — All Rights Reserved.
                        </div>
                    </div>
                </div>
            """.formatted(name, email, message.replace("\n", "<br>"));

            adminHelper.setText(adminHtml, true);
            mailSender.send(adminMsg);

            /* ========== 2️⃣  Send Auto-Reply to User ========== */
            MimeMessage autoMsg = mailSender.createMimeMessage();
            MimeMessageHelper userHelper = new MimeMessageHelper(autoMsg, true, "UTF-8");

            userHelper.setFrom("bhavesh.shahare05@gmail.com", "Tarana Soya Foods");
            userHelper.setTo(email);
            userHelper.setSubject("🌱 Thank you for contacting Tarana Soya Foods!");

            String userHtml = """
                <div style="font-family:'Poppins',sans-serif;background-color:#f7fff9;padding:25px;">
                    <div style="max-width:600px;margin:auto;background:white;border-radius:12px;box-shadow:0 3px 10px rgba(0,0,0,0.1);">
                        <div style="text-align:center;background-color:#2b7a3d;padding:20px 0;border-top-left-radius:12px;border-top-right-radius:12px;">
                        
                            <img src="https://lh3.googleusercontent.com/p/AF1QipOJnrOsvCQ2kuQ3-vADJaLeYOPM_jL_beQNbMaS=s1360-w1360-h1020-rw" width="120" alt="Tarana Soya Foods Logo">
                            <h2 style="color:white;margin:0;">Tarana Soya Foods</h2>
                        </div>
                        
                        
                        <div style="padding:25px;color:#333;">
                            <h3 style="color:#2b7a3d;">Dear %s,</h3>
                            <p>Thank you for reaching out to <strong>Tarana Soya Foods</strong>! 🌿</p>
                            <p>We’ve received your message and our team will get back to you shortly.</p>
                            <div style="margin:15px 0;padding:15px;background:#f1fdf5;border-left:4px solid #2b7a3d;border-radius:6px;">
                                <strong>Your message:</strong><br>%s
                            </div>
                            <p>Warm regards,<br><strong>The Tarana Soya Foods Team</strong></p>
                        </div>
                        <div style="background:#f8f8f8;padding:15px;text-align:center;border-bottom-left-radius:12px;border-bottom-right-radius:12px;font-size:13px;color:#555;">
                            &copy; 2025 Tarana Soya Foods | Healthy Protein-Rich Choices
                        </div>
                    </div>
                </div>
            """.formatted(name, message.replace("\n", "<br>"));

            userHelper.setText(userHtml, true);
            mailSender.send(autoMsg);

            /* ========== 3️⃣  UI Feedback ========== */
            redirectAttributes.addFlashAttribute("successMessage", "✅ Your message has been sent successfully! A confirmation email has been sent to you.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Failed to send message. Please try again later.");
        }

        return "redirect:/contact";
    }
}
