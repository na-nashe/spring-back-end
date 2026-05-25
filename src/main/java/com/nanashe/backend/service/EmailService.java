package com.nanashe.backend.service;

import com.nanashe.backend.util.HtmlTemplateLoader;
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

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${email.send.enabled}")
    private boolean emailSendingEnabled;

    private static final String HTML_TEMPLATE_NAME = "email-verification.html";

    public void sendVerificationEmail(String to, String token) {
        if (!emailSendingEnabled) {
            return;
        }

        String link = baseUrl + "/auth/email/verify?token=" + token;
        String html = HtmlTemplateLoader.loadTemplate(HTML_TEMPLATE_NAME)
                .replace("{link}", link);

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject("Підтвердіть вашу електронну пошту");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
}
