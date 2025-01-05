package com.lawzoom.complianceservice.dto.userDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String name;

    private Long roleId; // Role of the user (e.g., Master, Administrator)

    @NotBlank
    private String email;

    private Long typeOfResource; // Resource type (External/Internal)

    private boolean isEnable;

    private Long departmentId; // Department association

    private Long designationId; // Designation association

    private Long subscriptionId; // Required for non-Master users
}
