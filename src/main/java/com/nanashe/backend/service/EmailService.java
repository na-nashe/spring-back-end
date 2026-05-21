package com.nanashe.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(String to, String token) {
        String link = baseUrl + "/auth/verify?token=" + token;
        String html = """
                <div style="font-family:Arial,sans-serif;max-width:520px;margin:0 auto">
                  <h2>Verify your email address</h2>
                  <p>Thanks for signing up! Click the button below to verify your email.</p>
                  <a href="%s"
                     style="display:inline-block;padding:12px 24px;background:#4f46e5;color:#fff;
                            text-decoration:none;border-radius:6px;font-weight:bold">
                    Verify email
                  </a>
                  <p style="color:#6b7280;font-size:13px;margin-top:16px">
                    This link expires in 24 hours. If you didn't create an account, ignore this email.
                  </p>
                </div>
                """.formatted(link);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Verify your email");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
}
