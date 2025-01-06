package com.lawzoom.complianceservice.serviceImpl;//package com.authentication.serviceImpl;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//@Service
//public class MailSendSerivceImpl  {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    public void sendEmail(String[] emailTo, String[] ccPersons, String[] bccPersons) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//            helper.setFrom(fromEmail);
//            helper.setTo(emailTo);
//            helper.setCc(ccPersons);
//            helper.setBcc(bccPersons);
//            helper.setSubject("Kaushal Singh wants you to join Corpeed ERP");
//            helper.setText("Kaushal Here");
//
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
