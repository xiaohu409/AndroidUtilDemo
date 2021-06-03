package com.github.xiaohu409.androidutildemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);
        FlowView flowView = (FlowView) findViewById(R.id.flow_view_id);
        flowView.setSpeed(100f);
    }
}