package com.lawzoom.complianceservice.dto.complianceReminder;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequest {

    private Long reminderId;  // ✅ If provided, update existing reminder

    private Long milestoneId; // ✅ Optional: Can be linked to a milestone

    private Long subscriberId; // ✅ Required for both create and update

    private Long whomToSend;  // ✅ Optional: Who receives the reminder

    private LocalDate reminderDate;  // ✅ Optional for update

    private LocalDate reminderEndDate; // ✅ Optional for update

    @Min(value = 0, message = "Notification Timeline Value must not be negative")
    private Integer notificationTimelineValue;  // ✅ Optional for update

    @Min(value = 0, message = "Repeat Timeline Value must not be negative")
    private Integer repeatTimelineValue;  // ✅ Optional for update

    private String repeatTimelineType;  // ✅ Optional for update

    private Integer stopFlag = 1;  // ✅ Default to 1 if not provided

    private Long createdBy;  // ✅ Who created the reminder
}
