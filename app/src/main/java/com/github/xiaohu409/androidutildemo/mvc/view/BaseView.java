package com.github.xiaohu409.androidutildemo.mvc.view;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/27
 * 文件版本：1.0
 */
public interface BaseView<T> {

    /**
     * 成功
     * @param t
     */
    void onSuccess(T t);

    /**
     * 失败
     * @param t
     */
    void onFail(Throwable t);

    /**
     * 显示加载动画
     */
    void showLoad();

    /**
     * 隐藏加载动画
     */
    void hideLoad();
}
