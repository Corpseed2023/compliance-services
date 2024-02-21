package com.lawzoom.complianceservice.config;


public interface NotificationService {
    void sendNotification(String to, String subject, String message);
}
