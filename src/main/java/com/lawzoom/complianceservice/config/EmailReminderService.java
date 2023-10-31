package com.lawzoom.complianceservice.config;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailReminderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailReminder(Reminder reminder) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set the recipient email address (you can obtain it from the Reminder object)
            helper.setTo("kaushlendra.pratap@corpseed.com");

            // Set the email subject
//            helper.setSubject("Reminder: " + reminder.getSubject());
            helper.setSubject("Reminder: " + "Testing");


            // Set the email body (you can customize this part)
//            helper.setText("Hello,\n\nThis is a reminder for: " + reminder.getDescription());

            // Send the email
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle the exception or log the error
            e.printStackTrace();
        }
    }



    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}

