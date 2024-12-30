package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP,Long> {

    Optional<OTP> findByEmailContaining(String email);

    OTP findByEmailContainingAndOtpCode(String email, String otp);
}
