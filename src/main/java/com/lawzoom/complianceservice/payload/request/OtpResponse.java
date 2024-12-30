package com.lawzoom.complianceservice.payload.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OtpResponse {

    private String email;
    private String otp;
}
