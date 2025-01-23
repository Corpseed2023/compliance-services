package com.lawzoom.complianceservice.model.renewalModel;

import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "renewal")
public class Renewal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = true)
    private MileStone milestone;

    @Comment(value = "Date when the certificate was issued")
    private LocalDate issuedDate;

    @Comment(value = "Date when the certificate expires")
    private LocalDate expiryDate;

    private LocalDate renewalDate;


    @Enumerated(EnumType.STRING)
    @NotNull
    @Comment(value = "Type of duration for reminder (DAYS, WEEKS, MONTHS, YEARS)")
    private ReminderDurationType reminderDurationType;

    @Column(name = "reminder_duration_value", nullable = true)
    @Comment("Value of the duration (e.g., 10 for 10 days before expiry)")
    private Integer reminderDurationValue;

    @Comment(value = "Start date for sending reminders")
    private LocalDate reminderStartDate;

    @Comment(value = "Notes or additional details about the renewal")
    @Column(name = "renewal_notes", length = 500)
    private String renewalNotes;

    @Comment(value = "Flag to enable or disable renewal notifications")
    private boolean notificationsEnabled = true;

    @Comment(value = "Frequency of reminders sent")
    @Column(name = "reminder_frequency", columnDefinition = "integer default 0")
    private int reminderFrequency = 0;

    @Comment(value = "Indicator if the reminder has been sent")
    private boolean reminderSent = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    public void calculateNextReminderDate() {
        if (this.reminderDurationValue != null && this.reminderDurationType != null) {
            switch (this.reminderDurationType) {
                case DAYS:
                    this.reminderStartDate = this.expiryDate.minusDays(this.reminderDurationValue);
                    break;
                case WEEKS:
                    this.reminderStartDate = this.expiryDate.minusWeeks(this.reminderDurationValue);
                    break;
                case MONTHS:
                    this.reminderStartDate = this.expiryDate.minusMonths(this.reminderDurationValue);
                    break;
                case YEARS:
                    this.reminderStartDate = this.expiryDate.minusYears(this.reminderDurationValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid reminder duration type: " + this.reminderDurationType);
            }
        } else {
            throw new IllegalArgumentException("Reminder duration type and value must not be null.");
        }
    }

    public enum ReminderDurationType {
        DAYS, WEEKS, MONTHS, YEARS
    }
}
