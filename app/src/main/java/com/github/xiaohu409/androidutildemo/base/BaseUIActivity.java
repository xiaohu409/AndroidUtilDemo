package com.github.xiaohu409.androidutildemo.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.github.xiaohu409.androidutildemo.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名称：AndroidUtilDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/9/26
 * 文件版本：1.0
 */
public abstract class BaseUIActivity extends BaseActivity implements BaseUI, BaseData, View.OnClickListener {

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        setContentView(getView());
        unbinder = ButterKnife.bind(this);
        initUI();
        bindData();
    }

    @Override
    public void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void bindData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
    }
}
