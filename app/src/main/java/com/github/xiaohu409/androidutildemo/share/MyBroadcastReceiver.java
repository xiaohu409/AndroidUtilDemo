package com.github.xiaohu409.androidutildemo.share;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.github.xiaohu409.androidutil.LogUtil;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName clickName = intent.getParcelableExtra(Intent.EXTRA_CHOSEN_COMPONENT);
        LogUtil.logDebug(TAG, "click:" +  clickName);
    }
}
