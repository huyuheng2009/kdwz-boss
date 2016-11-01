/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author zeng
 */
public class DateUtil {

    /**
     * 将日期转换成字符串
     *
     * @param date 待转换日期
     * @param pattern 转换格式模板
     * @return 格式化转换后的字符串
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将字符串转换成日期
     *
     * @param strDate Tue Jan 11 00:00:00 CST 2011
     * @return
     */
    public static Date stringToDate(String strDate, Locale locale) {
        if (StringUtil.isEmptyString(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy", locale);
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转换成日期
     *
     * @param strDate Tue Jan 11 00:00:00 CST 2011
     * @return
     */
    public static Date stringToDate(String strDate) {
        if (StringUtil.isEmptyString(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转换成日期
     *
     * @param strDate Tue Jan 11 00:00:00 CST 2011
     * @return
     */
    public static Date stringToDate1(String strDate) {
        if (StringUtil.isEmptyString(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLastDay(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 11);
        return sdf.format(c.getTime());
    }

    /**
     * 将日期转换成日期
     *
     * @param strDate Tue Jan 11 00:00:00 CST 2011
     * @return
     */
    public static Date DateToDate(Date strDate) {
        if (StringUtil.isEmptyString(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return DateUtil.stringToDate1(sdf.format(strDate));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转换成日期
     *
     * @param strDate - 日期字符串
     * @param pattern - 格式模板
     * @param locale - strDate对应的locale
     * @return 格式化转换后的日期
     */
    public static Date stringToDate(String strDate, String pattern,
            Locale locale) {
        if (StringUtil.isEmptyString(strDate)) {
            return null;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                pattern, locale);

        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String StringToString(String date, String pattern) {
        if (StringUtil.isEmptyString(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date da = sdf.parse(date);
            return DateUtil.dateToString(da, pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将日期转换成日期
     *
     * @param date
     * @return
     */
    public static Date DateToStartDate(Date date) {
        if (StringUtil.isEmptyString(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String da = sdf.format(date) + " 00:00:00";
        return DateUtil.stringToDate(da);

    }

    /**
     * 将日期转换成日期
     *
     * @param date
     * @return
     */
    public static Date DateToEndDate(Date date) {
        if (StringUtil.isEmptyString(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String da = sdf.format(date) + " 23:59:59";
        return DateUtil.stringToDate(da);

    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static String getStrCurrentDate(String pattern) {
        Calendar c = Calendar.getInstance();
        String date = DateUtil.dateToString(c.getTime(), pattern);
        return date;
    }

    /**
     * 获取当前时间减一天开始时间
     *
     * @return StringUtil.java Date
     */
    public static String getMinusDayStartDate() {

        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        c.add(Calendar.DATE, -1);
        return getStartData(c.getTime());
    }

    /**
     * 获取当前时间减一天结束时间
     *
     * @return StringUtil.java Date
     */
    public static String getMinusDayEndDate() {

        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        c.add(Calendar.DATE, -1);
        return getEndData(c.getTime());
    }

    public static Date getMinusDate(int minus) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, minus);
        return c.getTime();
    }

    public static Date getHouserDate(Date date, int hourseDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hourseDate);
        return c.getTime();
    }

    public static String getHouserStr(Date date, int hourseDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hourseDate);
        return dateToString(c.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得开始时间段
     *
     * @param date
     * @return
     */
    public static String getStartDateTime(String date) {
        if (!StringUtil.isEmptyString(date)) {
            date = date + " 00:00:00";
        }
        return date;
    }

    /**
     * 获得结束时间段
     *
     * @param date
     * @return
     */
    public static String getEndDateTime(String date) {
        if (!StringUtil.isEmptyString(date)) {
            date = date + " 23:59:59";
        }
        return date;
    }

    /**
     * 根据日期等到该日期的第一秒
     *
     * @param d
     * @return
     */
    public static String getStartData(Date d) {

        if (d == null) {
            return null;
        }
        return DateUtil.dateToString(d, "yyyy-MM-dd") + " 00:00:00";
    }

    /**
     * 根据日期等到该日期的最后一秒
     *
     * @param d
     * @return
     */
    public static String getEndData(Date d) {

        if (d == null) {
            return null;
        }
        return DateUtil.dateToString(d, "yyyy-MM-dd") + " 23:59:59";
    }

    /**
     * 时间unix转换
     *
     * @param timestampString
     * @return
     */
    public static String TimeStampDate(String timestampString, String format) {

        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(format)
                .format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 将时间unix转换为int类型
     *
     * @param timeString
     * @param format
     * @return
     */
    public static int DateToInt(String timeString, String format) {

        int time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(timeString);
            String strTime = date.getTime() + "";
            strTime = strTime.substring(0, 10);
            time = Integer.parseInt(strTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间unix转换为int类型
     *
     * @param date
     * @return
     */
    public static int DateToInt(Date date) {
        int time = 0;

        String strTime = date.getTime() + "";
        strTime = strTime.substring(0, 10);
        time = Integer.parseInt(strTime);

        return time;
    }

    /**
     * 获得两日期的时间差
     *
     * @param date1
     * @param date2
     * @param type "hour","min","sec","ms"
     * @return
     */
    public static long getDiffTime(Date begin, Date end, String type) {
        long ret = 0;
        try {

            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            if ("ms".equalsIgnoreCase(type)) {
                ret = between * 1000;
            } else if ("sec".equalsIgnoreCase(type)) {
                ret = between;
            } else if ("min".equalsIgnoreCase(type)) {
                ret = between / 60;
            } else if ("hour".equalsIgnoreCase(type)) {
                ret = between / (60 * 60);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 得到本月的第一天时间
     *
     * @return
     */
    public static String getStartTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        String startTime = getStartData(c.getTime());
        return startTime;
    }

    /**
     * 得到本月的最后一天时间
     *
     * @return
     */
    public static String getEndTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endTime = getEndData(c.getTime());
        return endTime;
    }

    /**
     * 长整型转换为日期类型
     *
     * @param long longTime 长整型时间
     * @param String dataFormat 时间格式
     * @return String 长整型对应的格式的时间
     */
    public static String getLongString(long longTime, String dataFormat) {
        Date d = new Date(longTime);
        SimpleDateFormat s = new SimpleDateFormat(dataFormat);
        String str = s.format(d);
        return str;
    }

    public static Date getAddMinusDate(int date) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        return c.getTime();
    }

    public static Date getAddDay(int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    public static Date getAddDay(String date, int day) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat();
        Date d = s.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    public static String getDayCurrent() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(c.getTime());
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        c.add(Calendar.DATE, 1);
        //java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M月d日");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
        System.out.print(DateUtil.dateToString(c.getTime(), "yyyy"));

    }
}
