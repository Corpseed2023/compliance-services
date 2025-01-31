package com.lawzoom.complianceservice.dto.commonResponse;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequest {

    private Long userid;

    @NotNull(message = "Reminder Date is required")
    private LocalDate reminderDate;

    @NotNull(message = "Reminder End Date is required")
    private LocalDate reminderEndDate;

    @Min(value = 0, message = "Notification Timeline Value must not be negative")
    private int notificationTimelineValue;

    @Min(value = 0, message = "Repeat Timeline Value must not be negative")
    private int repeatTimelineValue;

    @NotNull(message = "Repeat Timeline Type is required")
    private String repeatTimelineType;

    private int stopFlag = 1;

    private Long createdBy;


}
