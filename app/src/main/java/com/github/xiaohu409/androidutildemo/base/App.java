package com.github.xiaohu409.androidutildemo.base;

import android.app.Application;

import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.SharePreUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.BuildConfig;

/**
 * 项目名称：ToolUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/7/23
 * 文件版本：1.0
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        ToastUtil.initToastUtil(this);
        SharePreUtil.initSharePreUtil(this, "test.xml");
        LogUtil.setDebug(BuildConfig.DEBUG);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //释放引用
        ToastUtil.releaseContext();
        SharePreUtil.release();
    }
}
