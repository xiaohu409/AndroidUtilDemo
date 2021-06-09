package com.github.xiaohu409.androidutildemo.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.github.xiaohu409.androidutildemo.R;

public class FlowView extends View {

    private final Rect mBound;
    private int part1Color;
    private int part2Color;
    private int part3Color;

    private int scaleFontColor;
    private int scaleFontSize;

    private int radius;
    private int arcWidth;
    private int offset;

    private float startAngle = 135f;
    private float sweepAngle = 90f;

    private Paint paint;
    private RectF rectF;

    private Paint textPaint;

    private String[] scaleText = {"0", "15", "30", "45", "60", "75", "90", "105", "120", "135"};

    private float percent;
    private float oldPercent = 0f;
    private float speed;
    private ValueAnimator valueAnimator;
    private long animatorDuration;

    private TimeInterpolator interpolator = new SpringInterpolator();

    public FlowView(Context context) {
        this(context, null);
    }

    public FlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.FlowView_part1_color:
                    part1Color = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.FlowView_part2_color:
                    part2Color = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.FlowView_part3_color:
                    part3Color = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.FlowView_scale_font_color:
                    scaleFontColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.FlowView_scale_font_size:
                    scaleFontSize = array.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics()));
                    break;
                case R.styleable.FlowView_radius:
                    radius = array.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()));
                    break;
                case R.styleable.FlowView_arc_width:
                    arcWidth = array.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()));
                    break;
                case R.styleable.FlowView_offset:
                    offset = array.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        rectF = new RectF();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(scaleFontSize);
        textPaint.setColor(scaleFontColor);
        mBound = new Rect();
        textPaint.getTextBounds(String.valueOf(speed), 0, String.valueOf(speed).length(), mBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        else {
            width = radius * 2 + arcWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        else {
            height = radius * 2 + arcWidth;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawArc(canvas);

        drawScale(canvas);

        drawPointer(canvas);

        drawUnit(canvas);

        drawSpeed(canvas);
    }

    //画表盘
    private void drawArc(Canvas canvas) {
//        paint.setColor(Color.BLUE);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        paint.setColor(part1Color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(arcWidth);

        int left = getWidth() / 2;
        int top = getHeight() / 2;
        rectF.set(left - radius, top - radius , left + radius , top + radius );
        canvas.drawArc(rectF, startAngle, sweepAngle, false,paint);
        paint.setColor(part2Color);
        canvas.drawArc(rectF, startAngle + sweepAngle, sweepAngle, false,paint);
        paint.setColor(part3Color);
        canvas.drawArc(rectF, startAngle + sweepAngle * 2, sweepAngle, false,paint);
        //圆心
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), 10, paint);
    }

    //画刻度
    private void drawScale(Canvas canvas) {
        canvas.save(); //记录画布状态
        paint.setColor(scaleFontColor);
        paint.setStrokeWidth(3);
        canvas.rotate(-90f, rectF.centerX(), rectF.centerY());
        float rAngle = sweepAngle * 3 / (scaleText.length - 1);
        for (int i = 0; i < scaleText.length  ; i++) {
            canvas.save(); //记录画布状态
            canvas.rotate(rAngle * i, rectF.centerX(), rectF.centerY());
            float startX = rectF.centerX() - radius + arcWidth * 4 - offset;
            float startY = rectF.centerY() - radius + arcWidth * 4 - offset;
            canvas.drawLine(startX, startY , startX + 15, startY + 15, paint);//画短刻度线

            canvas.drawText(scaleText[i] , startX + 25, startY + 35,  textPaint);
            canvas.restore();
        }
        canvas.restore();
    }

    //画指针
    private void drawPointer(Canvas canvas) {
        canvas.save();
        canvas.rotate(percent, rectF.centerX(), rectF.centerY());
        canvas.drawLine(rectF.centerX(), rectF.centerY(), rectF.centerX() - radius / 2 + 20 , rectF.centerY() + radius / 2 - 20, paint);
        canvas.restore();
    }

    //单位
    private void drawUnit(Canvas canvas) {
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(scaleFontSize / 1.2f);
        canvas.drawText("Kbps", rectF.centerX() - mBound.width() / 2 - arcWidth / 3, rectF.centerY() +  radius / 2  + arcWidth, textPaint);
    }

    //显示速度
    private void drawSpeed(Canvas canvas) {
        textPaint.setColor(Color.YELLOW);
        textPaint.setTextSize(scaleFontSize);
        canvas.drawText(String.valueOf(speed), rectF.centerX() - mBound.width() / 2, rectF.centerY() + radius - arcWidth, textPaint);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        percent = sweepAngle * 3 / 135 * speed;
        setAnimator(percent);
    }
    //动画
    private void setAnimator(final float percent) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }

        animatorDuration = (long) Math.abs(percent - oldPercent) * 20;

        valueAnimator = ValueAnimator.ofFloat(oldPercent, percent).setDuration(animatorDuration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                FlowView.this.percent = (float) animation.getAnimatedValue();
                invalidate();

            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oldPercent = percent;
            }
        });
        valueAnimator.start();
    }
}
