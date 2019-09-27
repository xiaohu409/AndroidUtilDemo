package com.github.xiaohu409.androidutildemo.mvp.net;

import com.github.xiaohu409.androidutildemo.mvp.bean.LoginBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 接口
 * hutao
 * 2018/3/20
 */
public interface TMApiService {

    //登录
    String Login = "/api/login/login";
    @POST(Login)
    @FormUrlEncoded
    Call<LoginBean> login(@FieldMap Map<String, Object> param);

}
