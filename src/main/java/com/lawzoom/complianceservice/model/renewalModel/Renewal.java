package com.lawzoom.complianceservice.model.renewalModel;

import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
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
    @JoinColumn(name = "compliance_id", nullable = true)
    private Compliance compliance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = true)
    private MileStone milestone;

    @Comment(value = "Next renewal date for the compliance or milestone")
    @Column(name = "next_renewal_date", nullable = false)
    private LocalDate nextRenewalDate;

    @Comment(value = "Renewal frequency in months (e.g., 3, 6, 12)")
    @Column(name = "renewal_frequency", nullable = false)
    private int renewalFrequency;

    @Comment(value = "Renewal type (e.g., Half-Yearly, Yearly)")
    @Column(name = "renewal_type", nullable = false)
    private String renewalType;

    @Comment(value = "Notes or additional details about the renewal")
    @Column(name = "renewal_notes", length = 500)
    private String renewalNotes;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    @Comment(value = "Created timestamp")
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Comment(value = "Updated timestamp")
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
