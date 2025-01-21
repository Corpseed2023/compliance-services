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
    private String applicableZone;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private int priority;
    private Long companyId;
    private Long businessUnitId;
    private Long businessActivityId;
    private Long subscriberId;
    private double progressPercentage;
    private List<ComplianceMilestoneResponse> milestones;
    private int MilestoneCount;


}
