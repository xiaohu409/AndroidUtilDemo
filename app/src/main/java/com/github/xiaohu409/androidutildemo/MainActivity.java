package com.github.xiaohu409.androidutildemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.xiaohu409.androidutil.SharePreUtil;
import com.github.xiaohu409.androidutil.ToastUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ToastUtil.showLong("显示");
        SharePreUtil.getInstance().put("name", "胡涛");
        SharePreUtil.getInstance().put("age", 22);
        SharePreUtil.getInstance().put("height", 1.75);
        SharePreUtil.getInstance().put("man", true);
       ToastUtil.showLong(SharePreUtil.getInstance().getString("name"));
    }
}
