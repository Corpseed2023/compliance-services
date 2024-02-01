package com.lawzoom.complianceservice.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class NotBeforeTodayValidator implements ConstraintValidator<NotBeforeToday, Date> {

    @Override
    public boolean isValid(Date value, final ConstraintValidatorContext context) {
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        value = c.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date today = calendar.getTime();

        System.out.println("Provided Date: " + value);
        System.out.println("Today's Date: " + today);
        System.out.println("Comparison Result: " + (value.after(today) || value.equals(today)));

        return value.after(today) || value.equals(today);
    }


}
