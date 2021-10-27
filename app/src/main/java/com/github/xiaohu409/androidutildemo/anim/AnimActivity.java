package com.github.xiaohu409.androidutildemo.anim;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.xiaohu409.androidutildemo.A;
import com.github.xiaohu409.androidutildemo.R;

public class AnimActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        initUI();
    }

    public void initUI() {
        imageView = findViewById(R.id.anim_iv_id);
        Button startBtn = findViewById(R.id.start_btn_id);
        startBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        frameAnimCodeImpl();
//        fameAnimXmlImpl();
        tweenAnimXmlImpl();
    }

    /**
     * 帧动画 xml实现
     */
    public void fameAnimXmlImpl() {
       Anim anim = new Anim() {
            @Override
            public void onAnim() {
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                animationDrawable.start();
            }
        };
       anim.onAnim();
    }

    /**
     * 帧动画 代码实现
     */
    public void frameAnimCodeImpl() {
        Anim anim = new Anim() {
            @Override
            public void onAnim() {
                AnimationDrawable animationDrawable = new AnimationDrawable();
//                int[] frame = new int[]{R.drawable.anim1, R.drawable.anim2, R.drawable.anim3, R.drawable.anim4,
//                        R.drawable.anim5, R.drawable.anim6, R.drawable.anim7, R.drawable.anim8};
//                for (int rId : frame) {
//                    Drawable drawable = getDrawable(rId);
//                    animationDrawable.addFrame(drawable, 200);
//                }
                for (int i = 1; i < 9; i++) {
                    int rId = getResources().getIdentifier("anim" + i, "drawable", getPackageName());
                    Drawable drawable = getDrawable(rId);
                    animationDrawable.addFrame(drawable, 200);
                }
                imageView.setImageDrawable(animationDrawable);
                animationDrawable.start();
            }
        };
        anim.onAnim();
    }

    public interface Anim {
        void onAnim();
    }

    //补间动画xml
    public void tweenAnimXmlImpl() {
        Anim anim = new Anim() {
            @Override
            public void onAnim() {
                Animation animation = AnimationUtils.loadAnimation(AnimActivity.this, R.anim.alpha_anim);
                imageView.startAnimation(animation);
            }
        };
        anim.onAnim();
    }
}