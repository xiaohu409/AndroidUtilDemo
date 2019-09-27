package com.github.xiaohu409.androidutildemo.mvp.net;


import retrofit2.Retrofit;

/**
 * 项目名称：     SanSiXian
 * 文件名：       TMApiManager
 * 描述：
 * 作者：         胡涛
 * 日期：         2019/2/15
 * 版本：         v1.0
 */
public class TMApiManager {

    private static TMApiManager manager;
    private TMApiService apiService;

    private TMApiManager() {
        Retrofit retrofit = RetrofitUtil.newInstance().getRetrofit();
        apiService = retrofit.create(TMApiService.class);
    }

    public static TMApiManager newInstance() {
        if (manager == null) {
            synchronized (TMApiManager.class) {
                if (manager == null) {
                    manager = new TMApiManager();
                }
            }
        }
        return manager;
    }

    public TMApiService getApiService() {
        return apiService;
    }
}
