package com.github.xiaohu409.androidutildemo.filedownload;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.xiaohu409.androidutil.LogUtil;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.UnifiedListenerManager;

import java.io.File;
import java.util.Arrays;

public class Utils {

    private static final String TAG = "Utils";

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


    public static void printFilePath(Context context) {
        LogUtil.logDebug(TAG, context.getFilesDir().toString());
        LogUtil.logDebug(TAG, context.getCacheDir().toString());
        LogUtil.logDebug(TAG, context.getCodeCacheDir().toString());
        LogUtil.logDebug(TAG, context.getDatabasePath("test").toString());
        LogUtil.logDebug(TAG, context.getDir("", Context.MODE_PRIVATE).toString());
        LogUtil.logDebug(TAG, context.getNoBackupFilesDir().toString());
        LogUtil.logDebug(TAG, context.getFileStreamPath("filetest").toString());
        LogUtil.logDebug(TAG, context.getPackageCodePath());
        LogUtil.logDebug(TAG, context.getPackageResourcePath());

//        LogUtil.logDebug(TAG, new ApplicationInfo().dataDir);
//        LogUtil.logDebug(TAG, new ApplicationInfo().sourceDir);
//        LogUtil.logDebug(TAG, new ApplicationInfo().nativeLibraryDir);
//        LogUtil.logDebug(TAG, new ApplicationInfo().publicSourceDir);
//        LogUtil.logDebug(TAG, Arrays.toString(new ApplicationInfo().splitSourceDirs));
//        LogUtil.logDebug(TAG, Arrays.toString(new ApplicationInfo().splitPublicSourceDirs));
    }
}
