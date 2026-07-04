package com.campus.eventtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    public void sendReminderEmail(String to, String subject, String body) {
        try {
            if (mailSender != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(to);
                message.setSubject(subject);
                message.setText(body);
                message.setFrom("noreply@campuseventtracker.com");
                
                mailSender.send(message);
                System.out.println("Email sent to: " + to);
            } else {
                // Email not configured, just log
                System.out.println("EMAIL REMINDER (not sent - email not configured):");
                System.out.println("To: " + to);
                System.out.println("Subject: " + subject);
                System.out.println("Body: " + body);
                System.out.println("---");
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}