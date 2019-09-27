package com.github.xiaohu409.androidutildemo.mvp.presenter;

import com.github.xiaohu409.androidutildemo.mvp.bean.LoginBean;
import com.github.xiaohu409.androidutildemo.mvp.contract.AContract;
import com.github.xiaohu409.androidutildemo.mvp.model.AModel;
import com.github.xiaohu409.androidutildemo.mvp.view.LoginView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/26
 * 文件版本：1.0
 */
public class APresenter implements AContract.Presenter<LoginView<LoginBean>> {

    private AModel aModel;

    public APresenter() {
        this.aModel = new AModel();
    }

    @Override
    public void login(Map<String, Object> param, final LoginView<LoginBean> view) {
        view.showLoad();
        Call<LoginBean> beanCall = aModel.login(param);
        beanCall.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                view.hideLoad();
                view.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                view.hideLoad();
                view.onFail(t);
            }
        });
    }
}
