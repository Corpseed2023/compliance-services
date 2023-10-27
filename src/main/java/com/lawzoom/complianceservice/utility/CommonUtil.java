package com.lawzoom.complianceservice.utility;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@UtilityClass
public class CommonUtil {

    public String getCurrentTimeStamp() {
        final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf1.format(timestamp).replace(".", "");
    }

    public Date getDate() {
        return new Date();
    }

    public String getPassword(String companyName) {
        //fetch company
        String password = companyName.substring(0, 2) + getCurrentTimeStamp();
        if (password.length() > 12) password = password.substring(0, 12);
//		System.out.println("generated pass="+password);
        return password;
    }

    public String getCredentialMessage(String companyName, String companyEmail, String password) {
//		System.out.println("pass =="+password);
        String message = "<p>Hi " + companyName + ",</p><p>Your Login credential is : "
                + "</p><p>Username : " + companyEmail + "<br>Password : " + password + "</p>";

        return message;
    }

    public Date getExpiryDate(int hour) {
        // TODO Auto-generated method stub
        Calendar c = Calendar.getInstance();
        c.setTime(getDate());
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }


//	public static String getForgetEmailMessage(User user, String resetLink,Date date) {
//		String message="<p>Hi "+user.getUserFirstName()+",</p>"
//				+ "<p>Reset your password by clicking <a href=\""+resetLink+"\">here.</a><p/>"
//						+ "<p>This link is valid till : "+date+"</p>";
//		return message;
//	}

    public long getUniqueCode() {
        return (long) Math.floor(Math.random() * 9000000000L);
    }
	/*
	@SuppressWarnings({ "deprecation" })
	public static String getClientIp(HttpServletRequest request) {
		final String LOCALHOST_IPV4 = "127.0.0.1";
		final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
		String ipAddress = request.getHeader("X-Forwarded-For");
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
				try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					ipAddress = inetAddress.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!StringUtils.isEmpty(ipAddress) 
				&& ipAddress.length() > 15
				&& ipAddress.indexOf(",") > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		
		return ipAddress;
	}*/

    public boolean isValidPassword(String password) {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public String encodePassword(String password) {
        return password;
    }

    public Date parseToDate(Object object) {
        if (object == null) return null;
        String date = object.toString();
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int dayConsumed(Object d1, Object d2) {
        Date startDate = (Date) d1;
        Date completedDate = (Date) d2;
        int consumedDays = 0;

        if (startDate == null) return 0;

        if (completedDate == null) {
            consumedDays = daysBetweenDate(startDate, getDate());
        } else {
            consumedDays = daysBetweenDate(startDate, completedDate);
        }
        System.out.println("Day Consumed==" + consumedDays);

        return consumedDays;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static int daysBetweenDate(Date startDate, Date endDate) {
        System.out.println(startDate + "#" + endDate);
        Period period = Period.between(convertToLocalDateViaInstant(startDate),
                convertToLocalDateViaInstant(endDate));
        return period.getDays();
    }

    public int totalDays(Object d1, Object d2) {
        Date startDate = (Date) d1;
        Date dueDate = (Date) d2;

        if (startDate == null || dueDate == null)
            return 0;

        int totalDays = daysBetweenDate(startDate, dueDate);
        System.out.println("Total Days==" + totalDays);

        return totalDays;
    }

    public int expectedProgress(Object d1, Object d2) {
        Date startDate = (Date) d1;
        Date dueDate = (Date) d2;
        int expectedProgress = 0;

        if (startDate == null || dueDate == null)
            return 0;

        int days = daysBetweenDate(startDate, dueDate);
        if (days > 0) {
            int daysToday = daysBetweenDate(startDate, getDate());
            if (daysToday > days) expectedProgress = 100;
            else
                expectedProgress = (100 / days) * daysToday;
        }
        System.out.println("Expected Progress==" + expectedProgress);
        return expectedProgress;
    }


}
