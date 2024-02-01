package com.lawzoom.complianceservice.model.reminderModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawzoom.complianceservice.annotation.NotBeforeToday;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.Comment;
import jakarta.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "taskReminder")
public class TaskReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ComplianceTask.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "compliance_task_id")
    private ComplianceTask complianceTask;


    @OneToOne(targetEntity = ComplianceSubTask.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "compliance_sub_task_id")
    private ComplianceSubTask complianceSubTask;

    @Column(name = "reminder_date")
    @NotBeforeToday(message = "Please enter future date..!!")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reminderDate;

    @Column(name = "reminder_end_date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date reminderEndDate;

    private String taskRemark;

    private Long setByUser;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;

    @Column(length = 1, name="is_enable", columnDefinition = "tinyint(1) default 1")
    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    @Column(name = "reminder_sent", columnDefinition = "tinyint(1) default 0")
    @Comment(value = "0: Not sent, 1: Sent")
    private boolean reminderSent;
}
