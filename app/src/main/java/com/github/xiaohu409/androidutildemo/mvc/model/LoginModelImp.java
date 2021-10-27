package com.github.xiaohu409.androidutildemo.mvc.model;

import androidx.annotation.NonNull;

import com.github.xiaohu409.androidutildemo.mvc.bean.LoginBean;
import com.github.xiaohu409.androidutildemo.mvc.net.ApiManager;
import com.github.xiaohu409.androidutildemo.mvc.view.LoginView;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/27
 * 文件版本：1.0
 */
public class LoginModelImp implements LoginModel<Response<LoginBean>> {

    @Override
    public void login(Map<String, Object> param, final LoginView<Response<LoginBean>> view) {
        view.showLoad();
        ApiManager.newInstance()
                .getApiService()
                .login(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Response<LoginBean>, Observable<Response<LoginBean>>>() {
                    @Override
                    public Observable<Response<LoginBean>> apply(@NonNull Response<LoginBean> response) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Response<LoginBean>>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Response<LoginBean>> emitter) throws Exception {
                                response.body().getData().setNickname("胡桃");
                                emitter.onNext(response);
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .subscribe(new Observer<Response<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Response<LoginBean> value) {
                        view.hideLoad();
                        System.out.println("onNext " + Thread.currentThread().getName());
                        view.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoad();
                        System.out.println("onError " + Thread.currentThread().getName());
                        view.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoad();
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
