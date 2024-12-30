package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.model.OTP;
import com.lawzoom.complianceservice.payload.request.OtpResponse;

public interface OtpService {


    OtpResponse generateOtp(String mobile, String name);

    OTP findOtpByEmailAndOtpCode(String mobile, String otp);

    void sendOtpOnEmail(String email, String otp, String name);

}
