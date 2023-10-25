package com.lawzoom.complianceservice.dto.businessUnitDto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusinessUnitResponse {

	private Long id;

	private String businessActivity;

	private String city;

	private String locatedAt;

	private int permanentEmployee;

	private int contractEmployee;

	private String address;

	private Date createdAt;

	private boolean isEnable;

}
