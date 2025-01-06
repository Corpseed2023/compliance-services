package com.lawzoom.complianceservice.dto.complianceDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyComplianceDTO {

    private Long companyId;
    private String companyName;
    private Long businessActivityId;
    private String businessActivity;
    private LocalDate date;
    private Long gstDetailsId;
    private String gstNumber;
    private Long businessUnitId;
    private String businessAddress;
    private long complianceCount;

}
