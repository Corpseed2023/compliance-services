package com.lawzoom.complianceservice.dto.industryDTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessActivityResponseDTO {

    private Long id;

    private String businessActivityName;

    private Long industrySubCategoryId;

    private Long industryCategoryId;
}
