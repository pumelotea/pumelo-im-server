package io.pumelo.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 该工具 支持jdk1.8 以上的版本
 */
public class NewDateUtils extends org.apache.commons.lang3.time.DateUtils {
    /**
     * 示例 采用jdk1.8 获取当前具体时间 格式（"yyyy-MM-dd HH:mm:ss"）
     * @return
     */
    public static String formatNowDateTime(){
        String format = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return format.replace("T"," ").replace("."," ");
    }

    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     * @date
     * @result
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     * @result
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     * @result
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     * @result
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     * @result
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     * @result
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     * @result
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 把double 类型转换成对应的日期类型
     * @param d
     * @return
     */
    public static String formatDoubleToDateTime(Double d,String format){
        Long l = d.longValue();
        return formatLongToDateTime(l,format);
    }

    public static String formatLongToDateTime(Long l,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.of("Asia/Shanghai")));
    }

    /**
     * 获取求时间区间趋势时所要求的时间区间
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }


    public static Date getDateStart(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断字符串是否是日期
     * @param timeString
     * @return
     */
    public static boolean isDate(String timeString){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try{
            format.parse(timeString);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * 格式化时间
     * @param timestamp
     * @return
     */
    public static String dateFormat(Date timestamp){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestamp);
    }

    /**
     * 获取系统时间Timestamp
     * @return
     */
    public static Timestamp getSysTimestamp(){
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获取系统时间Date
     * @return
     */
    public static Date getSysDate(){
        return new Date();
    }

    /**
     * 生成时间随机数
     * @return
     */
    public static String getDateRandom(){
        String s=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return s;
    }




}
