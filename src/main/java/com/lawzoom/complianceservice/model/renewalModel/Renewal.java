package com.lawzoom.complianceservice.model.renewalModel;

import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

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

    @Comment(value = "Type of duration for reminder (DAYS, WEEKS, MONTHS, YEARS)")
    private String reminderDurationType;

    @Column(name = "reminder_duration_value", nullable = true)
    @Comment("Value of the duration (e.g., 10 for 10 days before expiry)")
    private Integer reminderDurationValue;

    @Comment(value = "Start date for sending reminders")
    private LocalDate reminderStartDate;

    @Comment(value = "Notes or additional details about the renewal")
    @Column(name = "renewal_notes", length = 500)
    private String renewalNotes;

    @Comment(value = "Flag to enable or disable renewal notifications")
    private boolean stopFlag = false;

    @Comment(value = "Frequency of reminders sent")
    @Column(name = "reminder_frequency", columnDefinition = "integer default 0")
    private int reminderFrequency = 0;

    @Comment(value = "Indicator if the reminder has been sent")
    private boolean reminderSent = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    @Comment(value = "Created timestamp")
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Comment(value = "Updated timestamp")
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    /**
     * Updates the next reminder date and sets the reminder start date based on the expiry date and reminder duration.
     */
    public void calculateNextReminderDate() {
        if (this.reminderDurationValue != null && this.reminderDurationType != null) {
            // Calculate the start date for sending reminders
            switch (this.reminderDurationType.toUpperCase()) {
                case "DAYS":
                    this.reminderStartDate = this.expiryDate.minusDays(this.reminderDurationValue);
                    break;
                case "WEEKS":
                    this.reminderStartDate = this.expiryDate.minusWeeks(this.reminderDurationValue);
                    break;
                case "MONTHS":
                    this.reminderStartDate = this.expiryDate.minusMonths(this.reminderDurationValue);
                    break;
                case "YEARS":
                    this.reminderStartDate = this.expiryDate.minusYears(this.reminderDurationValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid reminder duration type: " + this.reminderDurationType);
            }

        } else {
            throw new IllegalArgumentException("Reminder duration type and value must not be null.");
        }
    }
}
