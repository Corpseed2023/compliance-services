package com.lawzoom.complianceservice.model.reminderModel;

import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task_reminder")
public class TaskReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull(message = "Task is required")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "Created By is required")
    private User createdBy;

    @Column(name = "reminder_date", nullable = false)
    @NotNull(message = "Reminder Date is required")
    private LocalDate reminderDate;

    @Column(name = "reminder_end_date", nullable = false)
    @NotNull(message = "Reminder End Date is required")
    private LocalDate reminderEndDate;

    @Comment("Notification trigger timeline in days before the due date")
    @Column(name = "notification_timeline_value", nullable = false)
    @Min(value = 0, message = "Notification timeline value must not be negative")
    private int notificationTimelineValue;

    @Comment("Number of intervals between repeated notifications")
    @Column(name = "repeat_timeline_value", nullable = false)
    @Min(value = 0, message = "Repeat timeline value must not be negative")
    private int repeatTimelineValue;

    @Comment("Type of repeat timeline: daily, weekly, etc.")
    @Column(name = "repeat_timeline_type", nullable = false)
    @NotNull(message = "Repeat Timeline Type is required")
    private String repeatTimelineType;

    @Comment("Flag to stop the reminders")
    @Column(name = "stop_flag", columnDefinition = "integer default 1")
    private int stopFlag = 1;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
