package com.liufeng.common.utils;

import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.exception.BusinessException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类,主要是以java8提供的线程安全的API获取Date
 * {@link Instant}  表示与时区无关的时刻
 * {@link LocalDateTime}  表示与时区无关日期和时间
 * {@link LocalDate}  表示与时区无关的日期
 * {@link LocalTime} 表示与时区无关的时间
 * {@link ZonedDateTime} 表示特定时区的日期和时间
 * {@link ZoneId}和 {@link ZoneOffset}  表示时区
 *
 * Java8提供的时间类型通过 {@link Instant}与 {@link Date}进行转换
 * java8时间和 {@link Instant}转换时必须添加时区
 */
public class DateUtils {

    public static final String DATETIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMATTER = "yyyy-MM-dd";
    public static final String TIME_FORMATTER = "HH:mm:ss";

    /**
     * LocalDateTime转Date
     */
    public static Date localDateTime2Date(LocalDateTime time) {
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        //Date date = Date.from(time.toInstant(ZoneOffset.ofHours(8)));   //同上
        return date;
    }

    /**
     * LocalDateTime字符串转Date
     */
    public static Date localDateTimeString2Date(String date, String formatter){
        return localDateTime2Date(LocalDateTime.parse(date,DateTimeFormatter.ofPattern(formatter)));
    }


    /**
     * LocalDate转Date
     */
    public static Date localDate2Date(LocalDate time) {
        Date date = localDateTime2Date(time.atStartOfDay());
        return date;
    }

    /**
     * Date转LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date time) {
        LocalDateTime date = Instant.ofEpochMilli(time.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return date;
    }

    /**
     * Date转LocalDate
     */
    public static LocalDate date2LocalDate(Date time) {
        LocalDate date = Instant.ofEpochMilli(time.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    /**
     * 格式化时间
     */
    public static String localDateTime2String(LocalDateTime time, String formatter) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(formatter);
        return pattern.format(time);
    }

    /**
     * 判断时间是否早于某一时间
     */
    public static boolean isBefore(LocalDateTime time, LocalDateTime target, String message) {
        return time.isBefore(target);

    }

    /**
     * 判断时间是否晚于某一时间
     */
    public static boolean isAfter(LocalDateTime time, LocalDateTime target, String message) {
        return time.isAfter(target);
    }

    /**
     * 判断时间是否等于某一时间
     */
    public static boolean isEqual(LocalDateTime time, LocalDateTime target, String message) {
        return time.isEqual(target);
    }

    /**
     * 计算时间间隔(单位为日以下)
     */
    public static Duration durationTimeLag(LocalDateTime start, LocalDateTime end){
        Duration between = Duration.between(start, end);
        return between;
    }

    /**
     * 计算时间间隔(单位日以上)
     */
    public static Period periodTimeLag(LocalDate start, LocalDate end){
        Period between = Period.between(start, end);
        return between;
    }


}
