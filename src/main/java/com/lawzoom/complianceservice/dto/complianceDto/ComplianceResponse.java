package com.lawzoom.complianceservice.dto.complianceDto;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceMilestoneResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceResponse {

    private Long id; // Unique ID of the compliance
    private String description;
    private String name; // Name of the compliance
    private String issueAuthority; // Authority issuing the compliance
    private Long durationMonth;
    private Long durationYear;
    private String certificateType; // Type of certificate, if applicable
    private String approvalState; // Current approval state (e.g., Approved, Pending)
    private String applicableZone; // Zone or region where the compliance is applicable
    private Date createdAt; // Timestamp for when the compliance was created
    private Date updatedAt; // Timestamp for when the compliance was last updated
    private boolean isEnable; // Status indicating if the compliance is active
    private LocalDate startDate; // Start date of the compliance
    private LocalDate dueDate; // Due date for compliance
    private LocalDate completedDate; // Date when the compliance was completed
    private int workStatus; // Work status (e.g., Pending, Completed, Not Applicable)
    private int priority; // Priority level of the compliance (Mandatory/Optional)
    private Long companyId; // Associated company ID
    private Long businessUnitId; // Associated business unit ID
    private Long createdBy; // User ID of the compliance creator
    private Long subscriberId; // Subscriber ID to whom the compliance is linked
    private String statusName;


    private double progressPercentage; // Progress percentage of compliance
    private List<ComplianceMilestoneResponse> milestones; // List of milestones for compliance
}