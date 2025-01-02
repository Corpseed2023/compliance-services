package com.lawzoom.complianceservice.dto.gstDTO.gstResponse;


import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GstCompanyResponse {

    private Long gstId;

    private String gstNumber;

    private Long countryId;
    private String countryName;

    private Long stateId;
    private String stateName;

    private Long companyId;
    private String companyName;

    private String gstRegistrationDate;

    private Long subscriberId;
    private String subscriptionName;

    private List<BusinessUnitResponse> businessUnitResponseList;
}
