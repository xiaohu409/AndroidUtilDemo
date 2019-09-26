package com.github.xiaohu409.androidutildemo;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.github.xiaohu409.androidutil.LogUtil;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/8/13
 * 文件版本：1.0
 */
public class A implements LifecycleObserver {

    private static final String TAG = "A";

    @OnLifecycleEvent(ON_START)
    public void println() {
        LogUtil.logDebug(TAG, "println()");
    }
}
