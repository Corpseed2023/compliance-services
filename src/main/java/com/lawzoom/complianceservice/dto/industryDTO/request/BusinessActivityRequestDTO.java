package com.lawzoom.complianceservice.dto.industryDTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessActivityRequestDTO {

    private Long id;

    private String businessActivityName;

    private Long industrySubCategoryId;

    private Long userId;


}
