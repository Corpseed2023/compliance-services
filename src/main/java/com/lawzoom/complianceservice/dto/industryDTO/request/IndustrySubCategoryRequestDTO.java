package com.lawzoom.complianceservice.dto.industryDTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndustrySubCategoryRequestDTO {

    private Long id;

    private String industrySubCategoryName;

    private Long industryCategoryId;

    private Long userId;
}
