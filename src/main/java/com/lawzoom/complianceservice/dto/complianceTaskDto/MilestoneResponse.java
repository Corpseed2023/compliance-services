package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class MilestoneResponse {
    private Long id;
    private String mileStoneName;
    private String description;
    private Long statusId;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Long complianceId;
    private Long managerId;
    private String reporterName;
    private Long assigned;
    private String assignedName;
    private Long assignedBy;
    private String assignedByName;
    private String assigneeMail;
    private LocalDate issuedDate;
    private String criticality;
    private Long businessUnitId;
    private Long subscriberId;
    private String remark;
    private LocalDate startedDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private int durationMonth;
    private int durationYear;


    // List of reminders
    private List<ReminderDetails> reminders;

    // List of renewals
    private List<RenewalDetails> renewals;

    // List of documents
    private List<DocumentDetails> documents;


    @Data
    public static class ReminderDetails {
        private Long id;
        private Date reminderDate;
        private Date reminderEndDate;
        private int notificationTimelineValue;
        private int repeatTimelineValue;
        private String repeatTimelineType;
        private Long whomToSendId;
        private String whomToSendName;
    }

    @Data
    public static class RenewalDetails {
        private Long id;
        private LocalDate nextRenewalDate;
        private int renewalFrequency;
        private String renewalType;
        private String renewalNotes;
    }

    @Data
    public static class DocumentDetails {

        private Long id;
        private String documentName;
        private String fileName;
        private Date issueDate;
        private String referenceNumber;
        private String remarks;
        private Date uploadDate;
    }
}
