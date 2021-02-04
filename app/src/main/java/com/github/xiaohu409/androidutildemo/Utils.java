package com.github.xiaohu409.androidutildemo;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.UnifiedListenerManager;

import java.io.File;

public class Utils {

    private static UnifiedListenerManager manager;

    public static UnifiedListenerManager getManager() {
        if (manager == null) {
            manager = new UnifiedListenerManager();
        }
        return manager;
    }

    public static File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
//        final File externalSaveDir = Environment.getExternalStorageDirectory();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }

    public static void calcProgressToView(ProgressBar progressBar, long offset, long total) {
        final float percent = (float) offset / total;
        progressBar.setProgress((int) (percent * progressBar.getMax()));
    }

    public static String getFileName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] result = url.split("/");
        if (result.length == 0) {
            return "";
        }
        String fileName = result[result.length - 1];
        return fileName;
    }

    public static DownloadTask createDownloadTask(Context context, TaskBean taskBean) {
        File parentFile = Utils.getParentFile(context);
        DownloadTask task = new DownloadTask.Builder(taskBean.getFileNameUrl(), parentFile)
                .setFilename(taskBean.getFileName())
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(1)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .setAutoCallbackToUIThread(true)
                .build();
        return task;
    }

}
