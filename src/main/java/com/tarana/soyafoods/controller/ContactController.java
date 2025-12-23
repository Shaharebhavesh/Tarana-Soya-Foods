package com.tarana.soyafoods.controller;

import com.tarana.soyafoods.service.GmailEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ContactController {

    @Autowired
    private GmailEmailService emailService;

    @PostMapping("/contact/send")
    public String sendContactMail(@RequestParam("name") String name,
                                  @RequestParam("email") String email,
                                  @RequestParam("message") String message,
                                  RedirectAttributes redirectAttributes) {

        try {
            // ================== BASIC VALIDATION ==================
            if (name.isBlank() || email.isBlank() || message.isBlank()) {
                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "❌ All fields are required."
                );
                return "redirect:/contact";
            }

            // ================== XSS SAFE VALUES ==================
            String safeName = HtmlUtils.htmlEscape(name.trim());
            String safeEmail = HtmlUtils.htmlEscape(email.trim());
            String safeMessage = HtmlUtils.htmlEscape(message.trim())
                    .replace("\n", "<br>");

            /* ================== ADMIN EMAIL ================== */
            String adminHtml = """
                <div style="font-family:'Poppins',sans-serif;background-color:#f7fff9;padding:25px;">
                  <div style="max-width:600px;margin:auto;background:white;border-radius:12px;">
                    <h2 style="color:#2b7a3d;">New Inquiry Received</h2>
                    <p><strong>Name:</strong> %s</p>
                    <p><strong>Email:</strong> %s</p>
                    <p><strong>Message:</strong></p>
                    <div style="background:#f1fdf5;padding:15px;border-left:4px solid #2b7a3d;">
                      %s
                    </div>
                  </div>
                </div>
            """.formatted(safeName, safeEmail, safeMessage);

            emailService.sendHtmlEmail(
                    "bhavesh.shahare05@gmail.com",
                    "📩 New Contact Form Message from " + safeName,
                    adminHtml
            );

            /* ================== USER AUTO-REPLY ================== */
            String userHtml = """
                <div style="font-family:'Poppins',sans-serif;background-color:#f7fff9;padding:25px;">
                  <div style="max-width:600px;margin:auto;background:white;border-radius:12px;">
                    <h3 style="color:#2b7a3d;">Dear %s,</h3>
                    <p>Thank you for contacting <strong>Tarana Soya Foods</strong>.</p>
                    <p>We have received your message:</p>
                    <div style="background:#f1fdf5;padding:15px;border-left:4px solid #2b7a3d;">
                      %s
                    </div>
                    <p>We will get back to you shortly.</p>
                  </div>
                </div>
            """.formatted(safeName, safeMessage);

            emailService.sendHtmlEmail(
                    safeEmail,
                    "🌱 Thank you for contacting Tarana Soya Foods!",
                    userHtml
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Your message has been sent successfully!"
            );

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "❌ Failed to send message. Please try again later."
            );
        }

        return "redirect:/contact";
    }
}
