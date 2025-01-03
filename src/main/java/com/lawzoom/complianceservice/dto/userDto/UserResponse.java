package com.lawzoom.complianceservice.dto.userDto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

	private Long id;

	private String email;

	private String designation;

	private String resourceType;

	private Date createdAt;

	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	private boolean isSubscribed;


}
