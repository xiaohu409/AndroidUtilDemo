package com.github.xiaohu409.androidutildemo.customview;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.customview.MyTextView;

public class TouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        MyTextView textView = findViewById(R.id.tv_id);
        textView.setOnClickListener(v -> ToastUtil.showShort("点了"));

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ToastUtil.showShort("触摸了");
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


}