package com.github.xiaohu409.androidutildemo.share;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;

public class ShareActivity extends BaseUIActivity {

    private TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void initUI() {
        super.initUI();
        textView = findViewById(R.id.text_tv_id);
    }

    @Override
    public void bindData() {
        super.bindData();
        Intent intent = getIntent();
        if (intent != null) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText(text);
        }

    }
}