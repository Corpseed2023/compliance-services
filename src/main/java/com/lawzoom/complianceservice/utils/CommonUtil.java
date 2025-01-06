//package com.lawzoom.complianceservice.utils;
//
//import com.lawzoom.complianceservice.model.OTP;
//import lombok.experimental.UtilityClass;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Random;
//
//@UtilityClass
//public class CommonUtil {
//
//    public static boolean isOtpExpired(OTP otp, String enteredOtp) {
//        if(otp==null||!otp.equals(enteredOtp))
//            return true;
//
//        return false;
//    }
//
//    public Date getDate(){return new Date();}
//
//    public LocalTime getCurrentDateAndTime(){
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalTime localTime = localDateTime.toLocalTime();
//        return localTime;
//    }
//
//    public String generateOTP(int length) {
//        String numbers = "1234567890";
//        Random random = new Random();
//        StringBuffer otp = new StringBuffer(length);
//
//        for(int i = 0; i< length ; i++) {
//            otp.append(numbers.charAt(random.nextInt(numbers.length())));
//        }
//        return otp.toString();
//    }
//
//    public Date getExpiryDateTime() {
//        Calendar calendar=Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.MINUTE,10);
//        return calendar.getTime();
//    }
//
//    public static String encodePassword(String password) {
//        return new BCryptPasswordEncoder().encode(password);
//    }
//
//    public static boolean isValidEmail(String email) {
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        return email.matches(emailRegex);
//    }
//
//    public static boolean isValidName(String name) {
//        // Allow only letters (both upper and lower case)
//        String nameRegex = "^[a-zA-Z]+$";
//        return name.matches(nameRegex);
//    }
//
//
//}
