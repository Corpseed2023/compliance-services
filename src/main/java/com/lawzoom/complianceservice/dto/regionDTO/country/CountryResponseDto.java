package com.lawzoom.complianceservice.dto.regionDTO.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDto {

    private Long id;

    private String countryName;

    private String countryCode;

}
