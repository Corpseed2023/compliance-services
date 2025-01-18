package com.lawzoom.complianceservice.model.complianceModel;

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
@Table(name = "compliance_history")
public class ComplianceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compliance_id", nullable = false)
    private Compliance compliance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action", nullable = false)
    @Comment("Type of action performed (e.g., CREATE, UPDATE, DELETE)")
    private String action;

    @Column(name = "action_details", length = 1000)
    @Comment("Details about the action performed")
    private String actionDetails;

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();
}
