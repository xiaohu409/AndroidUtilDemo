/*
 * Copyright (c) 2018 沈阳添美科技有限公司
 */

package com.github.xiaohu409.androidutildemo.base;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 做全局设置，不携带UI
 */
public class BaseActivity extends AppCompatActivity implements BasePermission {

    public static final int REQUEST_PERMISSION_CAMERA = 0x01;
    public static final int REQUEST_PERMISSION_GALLERY = 0x02;
    public static final int REQUEST_PERMISSION_CALL = 0x03;
    public static final int PERMISSION_REQUEST_COARSE_LOCATION = 0x04;
    public static final int REQUEST_PERMISSION_BLUETOOTH = 0x05;

    /**
     * 权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CAMERA:
                if (hasAllPermission(grantResults)) {
                    grantedPermission(REQUEST_PERMISSION_CAMERA);
                }
                else {
                    deniedPermission(REQUEST_PERMISSION_CAMERA);
                }
                break;
            case REQUEST_PERMISSION_GALLERY:
                if (hasAllPermission(grantResults)) {
                    grantedPermission(REQUEST_PERMISSION_GALLERY);
                }
                else {
                    deniedPermission(REQUEST_PERMISSION_GALLERY);
                }
                break;
            case REQUEST_PERMISSION_CALL:
                if (hasAllPermission(grantResults)) {
                    grantedPermission(REQUEST_PERMISSION_CALL);
                }
                else {
                    deniedPermission(REQUEST_PERMISSION_CALL);
                }
                break;
            case REQUEST_PERMISSION_BLUETOOTH:
                if (hasAllPermission(grantResults)) {
                    grantedPermission(REQUEST_PERMISSION_BLUETOOTH);
                }
                else {
                    deniedPermission(REQUEST_PERMISSION_BLUETOOTH);
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 是否有权限
     */
    private boolean hasAllPermission(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 授予权限
     * @param type
     */
    @Override
    public void grantedPermission(int type) {

    }

    /**
     * 拒绝权限
     * @param type
     */
    @Override
    public void deniedPermission(int type) {

    }


}
