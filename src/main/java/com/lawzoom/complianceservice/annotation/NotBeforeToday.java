//package com.lawzoom.complianceservice.annotation;
//
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = NotBeforeTodayValidator.class)
//@Documented
//public @interface NotBeforeToday {
//
//	 String message() default "{NotBeforeToday.message}";
//     Class<?>[] groups() default {};
//     Class<? extends Payload>[] payload() default {};
//}
