package com.github.xiaohu409.androidutil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * 项目名称：ToolUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/7/23
 * 文件版本：1.0
 */
public class ToastUtil {

    private static Context context;

    /**
     * 初始化ToastUtil
     * @param application
     */
    public static void initToastUtil(Application application) {
        context = application.getApplicationContext();
    }

    /**
     * 显示长时间toast
     * @param msg
     */
    public static void showLong(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示短时间的toast
     * @param msg
     */
    public static void showShort(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 释放context
     */
    public static void releaseContext() {
        context = null;
    }
}
