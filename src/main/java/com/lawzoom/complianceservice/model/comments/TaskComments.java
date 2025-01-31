package com.lawzoom.complianceservice.model.comments;

import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "task_comments")
public class TaskComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", nullable = false, columnDefinition = "TEXT")
    @Comment("The actual comment text provided by the user")
    private String commentText;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Comment("Timestamp of when the comment was created")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("The user who provided the comment")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @Comment("The task associated with the comment")
    private Task task;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
