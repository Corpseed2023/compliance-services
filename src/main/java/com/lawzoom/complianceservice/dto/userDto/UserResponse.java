package com.lawzoom.complianceservice.dto.userDto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
	private Long id;
	private String userName;
	private String email;
	private String mobile;
	private boolean isEnable;
	private boolean isAssociated;
	private Date createdAt;
	private Date updatedAt;
}
