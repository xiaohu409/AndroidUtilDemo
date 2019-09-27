package com.github.xiaohu409.androidutildemo.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/26
 * 文件版本：1.0
 */
public abstract class BaseUIActivity extends BaseActivity implements BaseUI, BaseData, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initUI();
        bindData();
    }

    @Override
    public void bindData() {

    }

}
