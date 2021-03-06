package com.github.xiaohu409.androidutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 项目名称：ToolUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/7/23
 * 文件版本：1.0
 */
public class DateTimeUtil {

    /**
     * 获取指定格式的时间
     * @param format
     * @return
     */
    public static String getDateTime(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 格式化日期
     * @param format
     * @param dateTime
     * @return
     */
    public static String formatDateTime(String format, String dateTime) {
        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        try {
            Date date = dateFormat.parse(dateTime);
            result = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化时间戳
     * @param format
     * @param time
     * @return
     */
    public static String formatDateTime(String format, long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(getDate(time));
    }

    /**
     * 获取date
     * @param time
     * @return
     */
    public static Date getDate(long time) {
        Date date = new Date();
        date.setTime(time);
        return date;
    }

}
