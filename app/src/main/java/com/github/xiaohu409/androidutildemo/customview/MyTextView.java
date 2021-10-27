package com.github.xiaohu409.androidutildemo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {

    private Random random = new Random();
    private int[] colors = new int[]{Color.BLUE, Color.RED, Color.GREEN,
            Color.YELLOW, Color.GRAY};

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        append(" ---");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return false;
        setTextColor(colors[random.nextInt(6) % colors.length]);
        return super.onTouchEvent(event);
//        ToastUtil.showShort("我消费了");
//        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
