package com.lawzoom.complianceservice.dto.teamMemberDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberRequest {

	@NotBlank
	private String name;

	private Long roleId;

	@NotBlank
	private String email;

	private String mobile;

	private Long typeOfResource;  //act as external or interenal resource

	private boolean isEnable;

	private Long reportingManagerId;

	private Long userId;

	private Long departmentId ;

	private Long designationId; // New field to map 'designation'

	private Long subscribedId;
}
