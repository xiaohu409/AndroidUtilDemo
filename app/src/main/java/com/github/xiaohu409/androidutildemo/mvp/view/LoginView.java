package com.github.xiaohu409.androidutildemo.mvp.view;

import com.github.xiaohu409.androidutildemo.mvp.contract.AContract;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/26
 * 文件版本：1.0
 */
public abstract class LoginView<T> implements AContract.View<T> {

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable throwable);
}
