package com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessUnitRequest {

	private long cityId;

	private long locatedAtId;

	private Long businessActivityId;

	private String address;

	private Date createdAt;

	private Date updatedAt;

	private Date dateRegistration;

	private String gstNumber;

	private long stateId;

	private Long subscriptionId;

	private Long userId;


}
