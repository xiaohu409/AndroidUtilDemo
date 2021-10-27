package com.github.xiaohu409.androidutildemo.mvc.net;


import retrofit2.Retrofit;

/**
 * 项目名称：     SanSiXian
 * 文件名：       TMApiManager
 * 描述：
 * 作者：         胡涛
 * 日期：         2019/2/15
 * 版本：         v1.0
 */
public class ApiManager {

    private static ApiManager manager;
    private ApiService apiService;

    private ApiManager() {
        Retrofit retrofit = RetrofitUtil.newInstance().getRetrofit();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiManager newInstance() {
        if (manager == null) {
            synchronized (ApiManager.class) {
                if (manager == null) {
                    manager = new ApiManager();
                }
            }
        }
        return manager;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
