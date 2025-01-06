package com.lawzoom.complianceservice.serviceImpl;//package com.authentication.serviceImpl;
//
//import com.lawzoom.complianceservice.model.SignupUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ForgotPasswordService {
//
//    @Autowired
//    private SignupUserRepository userRepository; // Create a repository for SignupUser
//
////    public void generateAndSendOTP(String email) {
////        String otp = generateRandomOTP();
////        SignupUser user = userRepository.findByEmail(email);
////        if (user != null) {
////            user.setOtp(otp);
////            userRepository.save(user);
////            sendOTPEmail(user.getEmail(), otp); // Implement this method to send OTP via email
////        }
//    }
//
//    public boolean verifyOTP(String email, String otp) {
//        SignupUser user = userRepository.findByEmail(email);
//        return user != null && user.getOtp() != null && user.getOtp().equals(otp);
//    }
//
//    public void resetPassword(String email, String newPassword) {
//        SignupUser user = userRepository.findByEmail(email);
//        if (user != null) {
//            user.setPassword(newPassword); // Assuming you have a setPassword method
//            user.setOtp(null); // Reset OTP after successful password change
//            userRepository.save(user);
//        }
//    }
//
//    private String generateRandomOTP() {
//        Random random = new Random();
//        int otpValue = 100000 + random.nextInt(900000);
//        return String.valueOf(otpValue);
//    }
//
//    private void sendOTPEmail(String email, String otp) {
//        // Implement this method to send OTP via email
//        // You can use JavaMailSender or an email service of your choice
//    }
//}
