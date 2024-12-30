package com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnitRequest {

    private Long userId;
    private Long subscriptionId;
    private Long companyId;
}
