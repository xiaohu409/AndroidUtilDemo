/*
 * Copyright (c) 2018 沈阳添美科技有限公司
 */

package com.github.xiaohu409.androidutildemo.base;

/**
 * 项目名称：     PLCX
 * 文件名：       BasePermission
 * 描述：         动态检查权限
 * 作者：         胡涛
 * 日期：         2018/3/30
 * 版本：         v1.0
 */
public interface BasePermission {

    /**
     * 授予权限
     */
    void grantedPermission(int type);

    /**
     * 拒绝权限
     */
    void deniedPermission(int type);
}
