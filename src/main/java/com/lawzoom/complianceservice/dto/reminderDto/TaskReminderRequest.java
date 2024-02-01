package com.lawzoom.complianceservice.dto.reminderDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawzoom.complianceservice.annotation.NotBeforeToday;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskReminderRequest {

    private Long complianceTaskId;

    private Long complianceSubTaskId;

    @NotBeforeToday(message = "Please enter a future date..!!")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date reminderDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date reminderEndDate;

    private String taskRemark;

    private Long setByUser;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;

    private boolean isEnable;
}
