package com.github.xiaohu409.androidutildemo.mvp.model;

import com.github.xiaohu409.androidutildemo.mvp.bean.LoginBean;
import com.github.xiaohu409.androidutildemo.mvp.contract.AContract;
import com.github.xiaohu409.androidutildemo.mvp.net.TMApiManager;

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
public class AModel implements AContract.Model {

    @Override
    public Call<LoginBean> login(Map<String, Object> param) {
        return TMApiManager.newInstance().getApiService().login(param);
    }
}
