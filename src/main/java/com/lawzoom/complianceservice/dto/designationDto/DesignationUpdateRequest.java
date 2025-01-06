package com.lawzoom.complianceservice.dto.designationDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DesignationUpdateRequest {

    private Long designationId;
    private String designationName;
    private Long userID;

}
