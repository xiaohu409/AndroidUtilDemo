package com.github.xiaohu409.androidutil;

import android.util.Log;

/**
 * 项目名称：ToolUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/7/23
 * 文件版本：1.0
 */
public class LogUtil {


    private static boolean isDebug;

    /**
     * 开启debug模式
     * @param value
     */
    public static void setDebug(boolean value) {
        isDebug = value;
    }

    /**
     * 输出debug日志
     * @param tag
     * @param msg
     */
    public static void logDebug(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    /**
     * 输出错误日志
     * @param tag
     * @param msg
     */
    public static void logError(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }
}
