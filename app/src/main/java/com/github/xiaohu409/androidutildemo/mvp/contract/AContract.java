package com.github.xiaohu409.androidutildemo.mvp.contract;

import com.github.xiaohu409.androidutildemo.mvp.bean.LoginBean;

import java.util.Map;

import retrofit2.Call;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/26
 * 文件版本：1.0
 */
public interface AContract {

    interface Model {
        Call<LoginBean> login(Map<String, Object> param);
    }

    interface View<T> {
        void showLoad();
        void hideLoad();
        void onSuccess(T t);
        void onFail(Throwable throwable);
    }

    interface Presenter<T extends View> {
        void login(Map<String, Object> param, T view);
    }
}
