package com.github.xiaohu409.androidutildemo.customview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.xiaohu409.androidutildemo.R;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);
        FlowView flowView = (FlowView) findViewById(R.id.flow_view_id);
        flowView.setSpeed(100f);
    }
}