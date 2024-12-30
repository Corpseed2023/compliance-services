package com.lawzoom.complianceservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    private List<String> roles;

}