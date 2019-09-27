/*
 * Copyright (c) 2018 沈阳添美科技有限公司
 */

package com.github.xiaohu409.androidutildemo.mvc.net;

import com.github.xiaohu409.androidutildemo.BuildConfig;

import net.sytm.sansixian.BuildConfig;
import net.sytm.sansixian.base.App;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * retrofit单例
 */
public class RetrofitUtil {

    private static final int timeout = 100;
    private Retrofit retrofit;
    private static RetrofitUtil retrofitUtil;

    private RetrofitUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(App.IP)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static RetrofitUtil newInstance() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

//    public static Retrofit getRetrofit(String ip) {
//        OkHttpClient httpClient = new OkHttpClient();
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
//        }
//        return new Retrofit.Builder()
//                .client(httpClient)
//                .baseUrl(ip)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//    }
}
