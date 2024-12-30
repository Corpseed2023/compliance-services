package com.lawzoom.complianceservice.dto.regionDTO.city;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityRequestDto {

    private Long id;

    private String cityName;

    private String cityCode;

    private Long stateId;


}
