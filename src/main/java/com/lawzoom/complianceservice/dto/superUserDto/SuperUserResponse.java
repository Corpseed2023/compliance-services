package com.lawzoom.complianceservice.dto.superUserDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SuperUserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String designation;

    private String email;

    private String mobile;
}
