package com.lawzoom.complianceservice.dto.businessUnitDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessUnitResponse {
	private Long id;
	private Long companyId;
	private String companyName;
	private Long cityId;
	private String city;
	private Long locatedAtId;
	private String locatedAt;
	private String address;
	private Date createdAt;
	private Date updatedAt;
	private boolean isEnable;
	private String gstNumber;
	private Long stateId;
	private String state;
	private Long businessActivityId;
	private String businessActivity;
}
