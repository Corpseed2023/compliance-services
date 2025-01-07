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
public class  UserRequest {

    @NotBlank
    private String name;

    private Long superAdminId;

    private Long roleId;

    private String mobile;

    @NotBlank
    private String email;

    private Long typeOfResource;  //act as external or interenal resource

    private boolean isEnable;

    private Long reportingManagerId;

    private Long departmentId ;

    private Long designationId; // New field to map 'designation'

    private Long subscriptionId;

}