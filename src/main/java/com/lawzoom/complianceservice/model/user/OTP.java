package com.lawzoom.complianceservice.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Size(min = 10, max = 15, message = "Mobile length should be between 10 and 15 digits.")
    @NotBlank(message = "Mobile number is required")
    private String mobile;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name = "otp_code", nullable = false)
    @NotBlank(message = "OTP code must not be blank")
    private String otpCode;

    @Column(nullable = false)
    private Long count = 0L;

    @Column(name = "is_used", columnDefinition = "tinyint(1) default 0")
    private boolean isUsed = false;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "expired_at", nullable = false)
    private Date expiredAt;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }


}
