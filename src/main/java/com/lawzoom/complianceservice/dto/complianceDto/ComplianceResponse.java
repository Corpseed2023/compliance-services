package com.lawzoom.complianceservice.dto.complianceDto;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceMilestoneResponse;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceResponse {

    private Long id;
    private String name;
    private String issueAuthority;
    private String certificateType;
    private String approvalState;
    private String applicableZone; // Zone or region where the compliance is applicable
    private Date createdAt; // Timestamp for when the compliance was created
    private Date updatedAt; // Timestamp for when the compliance was last updated
    private boolean isEnable; // Status indicating if the compliance is active
    private int workStatus; // Work status (e.g., Pending, Completed, Not Applicable)
    private int priority; // Priority level of the compliance (Mandatory/Optional)
    private Long companyId; // Associated company ID
    private Long businessUnitId; // Associated business unit ID
    private Long subscriberId; // Subscriber ID to whom the compliance is linked
    private double progressPercentage; // Progress percentage of compliance
    private List<ComplianceMilestoneResponse> milestones; // List of milestones for compliance
    private int MilestoneCount;

}
