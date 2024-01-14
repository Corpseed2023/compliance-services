package com.lawzoom.complianceservice.dto;


import lombok.Data;

@Data
public class TaskResponse {

    private Long companyId;
    private String companyName;
    private Long businessId;
    private String businessAddress;
    private Long complianceId;
    private String complianceName;
    private Long taskId;
    private String taskName;
    private String taskDescription;


}
