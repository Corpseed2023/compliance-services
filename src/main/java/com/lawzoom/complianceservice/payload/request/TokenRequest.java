package com.lawzoom.complianceservice.payload.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenRequest {

    private String username;
    private String password;
}
