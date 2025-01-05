package com.lawzoom.complianceservice.config;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.repository.RenewalRepository;
import com.lawzoom.complianceservice.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ComplianceNotificationScheduler {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private RenewalRepository renewalRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Scheduler for sending reminder emails.
     * Runs every day at 8:00 AM.
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendReminderEmails() {
        String today = dateFormat.format(new Date());
        List<Reminder> reminders = reminderRepository.findAll(); // Optimize with a query to fetch today's reminders

        for (Reminder reminder : reminders) {
            if (dateFormat.format(reminder.getReminderDate()).equals(today)) {
                try {
                    String recipientEmail = reminder.getWhomToSend().getEmail();
                    String recipientName = reminder.getWhomToSend().getUserName();

                    // Send email to the recipient
                    sendEmail(recipientEmail, "Compliance Reminder",
                            "Dear " + recipientName + ",<br><br>" +
                                    "This is a reminder for the compliance task associated with compliance ID: "
                                    + reminder.getCompliance().getId() + ".<br>" +
                                    "The task is due on: " + dateFormat.format(reminder.getReminderEndDate()) + ".<br><br>" +
                                    "Please take necessary actions.<br><br>" +
                                    "Best regards,<br>Compliance Management System");
                } catch (Exception e) {
                    System.err.println("Failed to send reminder email: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Scheduler for sending renewal emails.
     * Runs every day at 9:00 AM.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendRenewalEmails() {
        String today = dateFormat.format(new Date());
        List<Renewal> renewals = renewalRepository.findAll(); // Optimize with a query to fetch upcoming renewals

        for (Renewal renewal : renewals) {
            if (dateFormat.format(renewal.getNextRenewalDate()).equals(today)) {
                try {
                    String recipientEmail = renewal.getCompliance().getSubscriber().getSuperAdmin().getEmail();
                    String recipientName = renewal.getCompliance().getSubscriber().getSuperAdmin().getUserName();

                    // Send email to the subscriber
                    sendEmail(recipientEmail, "Compliance Renewal Reminder",
                            "Dear " + recipientName + ",<br><br>" +
                                    "This is a reminder for the renewal of compliance ID: "
                                    + renewal.getCompliance().getId() + ".<br>" +
                                    "The renewal is scheduled for today: " + dateFormat.format(renewal.getNextRenewalDate()) + ".<br><br>" +
                                    "Please proceed with the renewal process.<br><br>" +
                                    "Best regards,<br>Compliance Management System");
                } catch (Exception e) {
                    System.err.println("Failed to send renewal email: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Utility method to send email.
     *
     * @param toEmail   Recipient's email address.
     * @param subject   Email subject.
     * @param body      Email body.
     * @throws MessagingException If email sending fails.
     */
    private void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(message);
    }
}
