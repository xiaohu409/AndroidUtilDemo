package com.github.xiaohu409.androidutildemo.mvc.net;


import com.github.xiaohu409.androidutildemo.mvc.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 接口
 * hutao
 * 2018/3/20
 */
public interface ApiService {

    //登录
    String Login = "/user/login";
    @POST(Login)
    @FormUrlEncoded
    Observable<Response<LoginBean>> login(@FieldMap Map<String, Object> param);

}
