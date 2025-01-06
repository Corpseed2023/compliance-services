package com.lawzoom.complianceservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class AccessTypeDto {

    @NotBlank(message = "AccessType name is required")
    private String accessTypeName;

    private Date createdAt;

    private Date updatedAt;

    private boolean isEnable;
}
