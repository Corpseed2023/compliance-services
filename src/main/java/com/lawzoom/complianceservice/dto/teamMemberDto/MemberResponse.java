package com.lawzoom.complianceservice.dto.teamMemberDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

	private Long id;

	@NotBlank
	private String name;

	private String roleName;

	@NotBlank
	private String memberMail;

	private String mobile;


	private String typeOfResource;

	private boolean isEnable;

	private Long reportingManagerId;

	private String reportingManagerName;

	private Long subscriberId;

	private Long superAdminId;

	private String superAdminName;

	private Long departmentId;

	private String departmentName;

	private Long designationId;

	private String designationName;
}
