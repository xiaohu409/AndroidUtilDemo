package com.github.xiaohu409.androidutildemo.mvc.work;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2020/1/2
 * 文件版本：1.0
 */
public class UploadWorker extends Worker {

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("run.....");
        return Result.success();
    }

}
