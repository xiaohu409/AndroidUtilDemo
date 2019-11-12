package com.github.xiaohu409.androidutildemo.mvc.model;

import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.mvc.bean.BaseBean;
import com.github.xiaohu409.androidutildemo.mvc.bean.LoginBean;
import com.github.xiaohu409.androidutildemo.mvc.net.TMApiManager;
import com.github.xiaohu409.androidutildemo.mvc.view.LoginView;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/27
 * 文件版本：1.0
 */
public class LoginModelImp implements LoginModel<LoginBean> {

    @Override
    public void login(Map<String, Object> param, final LoginView<LoginBean> view) {
        TMApiManager.newInstance()
                .getApiService()
                .login(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(LoginBean value) {
                System.out.println("onNext " + Thread.currentThread().getName());
                view.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + Thread.currentThread().getName());
                //view.onFail(e);
            }

            @Override
            public void onComplete() {

            }
        });
//        loginBeanCall.enqueue(new Callback<LoginBean>() {
//            @Override
//            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
//                LoginBean bean = response.body();
//                if (bean == null) {
//                    return;
//                }
//                view.onSuccess(bean);
//            }
//
//            @Override
//            public void onFailure(Call<LoginBean> call, Throwable t) {
//                view.onFail(t);
//            }
//        });
    }
}
