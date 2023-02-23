package com.dtzhejiang.irs.res.bill.common.util;

import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter cnDateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    public static Date fromLocalDate(LocalDate localDate) {
        if(localDate == null) {
            return null;
        }
       return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getTime(String timeStr) {
        if(ObjectUtils.isEmpty(timeStr)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, timeFormatter);
        return fromLocalDateTime(localDateTime);
    }

    public static Date getStartTime(String dateStartStr) {
        if(ObjectUtils.isEmpty(dateStartStr)) {
            return null;
        }
        LocalDateTime startTime = LocalDate.parse(dateStartStr, dateFormatter).atStartOfDay();
        return fromLocalDateTime(startTime);
    }

    public static Date getEndTime(String dateEndStr) {
        if(ObjectUtils.isEmpty(dateEndStr)) {
            return null;
        }
        LocalDateTime endTime = LocalDate.parse(dateEndStr, dateFormatter).plusDays(1).atStartOfDay();
        return fromLocalDateTime(endTime);
    }

    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        if(date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if(date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static LocalDate toLocalDate(String dateStr) {
        if(ObjectUtils.isEmpty(dateStr)) {
            return null;
        }
        return LocalDate.parse(dateStr, dateFormatter);
    }

    public static LocalDateTime toLocalDateTime(String dateStr) {
        if(ObjectUtils.isEmpty(dateStr)) {
            return null;
        }
        return LocalDateTime.parse(dateStr,timeFormatter);
    }

    public static String convertLocalDate(LocalDate localDate) {
        if(localDate == null) {
            return "";
        }
        return localDate.format(dateFormatter);
    }

    public static String convertLocalDateTime(LocalDateTime dateTime) {
        if(dateTime == null) {
            return "";
        }
        return dateTime.format(timeFormatter);
    }


    public static String convertCnDate(Date date) {
        LocalDate localDate = toLocalDate(date);
        return convertCnLocalDate(localDate);
    }

    private static String convertCnLocalDate(LocalDate localDate) {
        if(localDate == null) {
            return  "";
        }
        return localDate.format(cnDateFormatter);
    }

    public static String convertDateTime(Date dateTime) {
        LocalDateTime localDateTime = toLocalDateTime(dateTime);
        return convertLocalDateTime(localDateTime);
    }
}
