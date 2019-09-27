package com.github.xiaohu409.androidutildemo.mvc.model;

import com.github.xiaohu409.androidutildemo.mvc.bean.BaseBean;
import com.github.xiaohu409.androidutildemo.mvc.view.LoginView;

import java.util.Map;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/27
 * 文件版本：1.0
 */
public interface LoginModel<T extends BaseBean> {

    void login(Map<String, Object> param, LoginView<T> view);
}
