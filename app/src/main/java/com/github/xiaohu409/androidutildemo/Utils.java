package com.github.xiaohu409.androidutildemo;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import java.io.File;

public class Utils {

    public static File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
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
        if (!TextUtils.isEmpty(url)) {
            return "";
        }
        String[] result = url.split("/");
        if (result.length == 0) {
            return "";
        }
        String fileName = result[result.length - 1];
        return fileName;
    }
}
