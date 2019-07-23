package com.github.xiaohu409.androidutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
}
