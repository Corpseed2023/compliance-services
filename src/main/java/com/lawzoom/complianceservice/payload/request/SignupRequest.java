package com.lawzoom.complianceservice.payload.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {

    private String name;

    private String email;

    private String otp;

    private String password;

    private String role;



}
