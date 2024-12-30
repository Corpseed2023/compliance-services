package com.lawzoom.complianceservice.dto.designationDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DesignationCreateRequest {

    private Long departmentId;
    private Long userId ;
    private String designationName;
}
