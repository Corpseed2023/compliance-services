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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamMemberResponse {


	@NotBlank
	private String memberName;

	private String roleName;

	@NotBlank
	private String memberMail;

	private String memberMobile;

	private String typeOfResource;

	private Date createdAt;

	private Date updatedAt;

	private boolean isEnable;

	private Long reportingManagerId;

	private Long subscriptionId;

	private Long createdBy;

	private Long superAdminId;

	private Long userId;


}
