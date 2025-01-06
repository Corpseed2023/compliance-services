package com.lawzoom.complianceservice.dto.gstDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GstDetailsFetchRequest {

    private Long gstDetailsId;
    private Long userId;
    private Long subscriberId;
    private Long companyId;
}
