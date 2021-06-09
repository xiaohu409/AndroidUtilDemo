package com.github.xiaohu409.androidutildemo.renderscript;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.renderscript.Allocation;
import androidx.renderscript.RenderScript;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.rs.ScriptC_Gray;


public class RenderScriptActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_script);
        initUI();
        bindData();
    }

    private void initUI() {
        imageView = findViewById(R.id.iv_id);
        Button proBtn = findViewById(R.id.pro_btn_id);
        proBtn.setOnClickListener(this);
    }

    private void bindData() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        bitmap = gray(this, bitmap);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 将 bitmap 去色后返回一张新的 Bitmap。
     */
    public static Bitmap gray(@NonNull Context context, @NonNull Bitmap bitmap) {

        // 创建输出 bitamp
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // 创建 RenderScript 对象
        RenderScript rs = RenderScript.create(context);

        // 创建输入、输出 Allocation
        Allocation allIn  = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        // 创建我们在上面定义的 script
        ScriptC_Gray script = new ScriptC_Gray(rs);

        // 对每一个像素执行 root 方法
        script.forEach_root(allIn, allOut);

        // 将执行结果复制到输出 bitmap 上
        allOut.copyTo(outBitmap);

        // 释放资源
        rs.destroy();
        return outBitmap;
    }

}