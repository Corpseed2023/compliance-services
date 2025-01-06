package com.lawzoom.complianceservice.dto.regionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDTO {

    private long id;

    private String cityName;

    private String cityCode;

}
