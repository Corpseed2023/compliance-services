package com.lawzoom.complianceservice.dto.complianceTaskDto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String managerName;
    private Long assignedId;
    private String assignedName;
    private Long assignedBy;
    private String assignedByName;
    private LocalDate issuedDate;
    private LocalDate startedDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private String criticality;
    private String remark;
    private Long businessUnitId;
    private Long subscriberId;

    private List<ReminderDetails> reminders;
    private List<RenewalDetails> renewals;
    private List<DocumentDetails> documents;

    private List<TaskMileStoneResponse> tasks;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReminderDetails {
        private Long id;
        private LocalDate reminderDate;
        private LocalDate reminderEndDate;
        private Integer notificationTimelineValue;
        private Integer repeatTimelineValue;
        private String repeatTimelineType;
        private Long whomToSendId;
        private String whomToSendName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RenewalDetails {
        private Long id;
        private LocalDate issuedDate;
        private LocalDate expiryDate;
        private String reminderDurationType;
        private Integer reminderDurationValue;
        private LocalDate nextReminderDate;
        private String renewalNotes;
        private boolean stopFlag;
        private int reminderFrequency;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentDetails {
        private Long id;
        private String documentName;
        private String fileName;
        private LocalDate issueDate;
        private String referenceNumber;
        private String remarks;
        private Date uploadDate;
    }
}
