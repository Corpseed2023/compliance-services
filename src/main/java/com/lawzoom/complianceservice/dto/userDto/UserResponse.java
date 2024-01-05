package com.lawzoom.complianceservice.dto.userDto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String mobile;

	private String designation;

	private String resourceType;

	private Date createdAt;

	private Date updatedAt;

	private boolean isEnable;

	private boolean isAssociated;

	private Long userId;


}
