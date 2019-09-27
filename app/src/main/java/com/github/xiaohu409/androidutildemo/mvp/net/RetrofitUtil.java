

package com.github.xiaohu409.androidutildemo.mvp.net;

import com.github.xiaohu409.androidutildemo.BuildConfig;

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

    private static final int TIMEOUT = 100;
    private static final String IP = "http://www.baidu.com";
    private Retrofit retrofit;
    private static RetrofitUtil retrofitUtil;

    private RetrofitUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(IP)
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

}
