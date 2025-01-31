package com.lawzoom.complianceservice.model.mileStoneModel;


import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "milestone_history")
public class MileStoneHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = false)
    private MileStone milestone;

    @Column(name = "operation", nullable = false)
    private String operation; // e.g., "CREATE", "UPDATE", "DELETE"

    @Column(name = "field_name", nullable = true)
    private String fieldName; // The field that was updated (if applicable)

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue; // Previous value of the field

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue; // New value of the field

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy; // The user who performed the operation

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }
}
