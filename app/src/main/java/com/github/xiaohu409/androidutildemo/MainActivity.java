package com.github.xiaohu409.androidutildemo;

import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.core.app.ActivityOptionsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.xiaohu409.androidutil.BluetoothUtil;
import com.github.xiaohu409.androidutil.DateTimeUtil;
import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.SharePreUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.anim.AnimActivity;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.bean.TestBean;
import com.github.xiaohu409.androidutildemo.bluetooth.BluetoothListActivity;
import com.github.xiaohu409.androidutildemo.customview.ViewActivity;
import com.github.xiaohu409.androidutildemo.databinding.ActivityMainBinding;
import com.github.xiaohu409.androidutildemo.filedownload.Utils;
import com.github.xiaohu409.androidutildemo.mvc.controller.LoginControllerActivity;
import com.github.xiaohu409.androidutildemo.mvc.controller.WorkManagerControllerActivity;
import com.github.xiaohu409.androidutildemo.renderscript.RenderScriptActivity;
import com.github.xiaohu409.androidutildemo.share.MyBroadcastReceiver;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.Manifest;

public class MainActivity extends BaseUIActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding activityMainBinding;
    private FrameLayout flVideoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        A a = new A();
        getLifecycle().addObserver(a);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public View getView() {
//        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
//        return activityMainBinding.getRoot();
        return null;
    }

    @Override
    public void initUI() {
        super.initUI();
        Button utilBtn = findViewById(R.id.test_util_btn_id);
        utilBtn.setOnClickListener(this);
        Button bluetoothBtn = findViewById(R.id.bluetooth_btn_id);
        bluetoothBtn.setOnClickListener(this);
        Button loginBtn = findViewById(R.id.login_btn_id);
        loginBtn.setOnClickListener(this);
//        activityMainBinding.loginBtnId.setOnClickListener(this);
////        Button testFastJsonBtn = findViewById(R.id.test_fast_btn_id);
//        activityMainBinding.testFastBtnId.setOnClickListener(this);
//
////        Button workManagerBtn = findViewById(R.id.work_btn_id);
//        activityMainBinding.workBtnId.setOnClickListener(this);
//
////        Button fileBtn = findViewById(R.id.file_btn_id);
//        activityMainBinding.fileBtnId.setOnClickListener(this);
//        if (BuildConfig.DEBUG) {
//            System.out.println(BuildConfig.IP);
//        }
//        flVideoContainer = findViewById(R.id.flVideoContainer);
//        WebView webView = findViewById(R.id.web_view_id);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setSupportZoom(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient() {
//            WebChromeClient.CustomViewCallback mCallback;
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback callback) {
//
//                fullScreen();
//
//                webView.setVisibility(View.GONE);
//                flVideoContainer.setVisibility(View.VISIBLE);
//                flVideoContainer.addView(view);
//                mCallback = callback;
//                super.onShowCustomView(view, callback);
//            }
//
//            @Override
//            public void onHideCustomView() {
//
//                fullScreen();
//
//                webView.setVisibility(View.VISIBLE);
//                flVideoContainer.setVisibility(View.GONE);
//                flVideoContainer.removeAllViews();
//                super.onHideCustomView();
//
//            }
//        });

//        webView.setWebViewClient(new WebViewClient() {
//
//            @Nullable
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                switch (request.getUrl().toString()) {
//                    default:
//                }
//                Map<String, String> header = new HashMap<>();
//                header.put("key", "value");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.loadUrl(request.getUrl().toString(), header);
//                    }
//                });
//                return super.shouldInterceptRequest(view, request);
//            }
//        });
//        webView.loadUrl("https://www.baidu.com");
////        String s = "hutao";
////        String pass = "EBD0B5D2-F4E7-453A-9E16-01AE8F6E2977";
////        String encoded = encrypt(s, pass);
////        //System.out.println("加密之前：{}" + s);
////        System.out.println("加密结果：{}" + encoded);
////        //decrypt(encoded, pass);
////        System.out.println("解密结果：{}" + decrypt(encoded, pass));
//
//        HtEditText editText = findViewById(R.id.input_et_id);
//        editText.setEditTextCallback((String s) -> ToastUtil.showShort(s));
//
//        Button intentBtn = findViewById(R.id.intent_btn_id);
//        intentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("hutao");
//                startActivity(intent);
//            }
//        });

        LottieAnimationView animationView = new LottieAnimationView(this);
        animationView.playAnimation();

        Button viewBtn = findViewById(R.id.view_btn_id);
        viewBtn.setOnClickListener(this);
        Button renderBtn = findViewById(R.id.render_btn_id);
        renderBtn.setOnClickListener(this);

        Button shareBtn = findViewById(R.id.share_btn_id);
        shareBtn.setOnClickListener(this);

        Button printBtn = findViewById(R.id.print_btn_id);
        printBtn.setOnClickListener(this);

        Button animBtn = findViewById(R.id.anim_btn_id);
        animBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_util_btn_id:
                //        ToastUtil.showLong("显示");
                SharePreUtil.getInstance().put("name", "胡涛");
                SharePreUtil.getInstance().put("age", new Random().nextInt(10));
                SharePreUtil.getInstance().put("height", 1.75);
                SharePreUtil.getInstance().put("man", true);
                ToastUtil.showLong(SharePreUtil.getInstance().getString("name"));
                LogUtil.logDebug(TAG, DateTimeUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
                break;
            case R.id.bluetooth_btn_id:
                startActivity(new Intent(this, BluetoothListActivity.class));
                break;
            case R.id.login_btn_id:
                //测试登录
                startActivity(new Intent(this, LoginControllerActivity.class));
                break;
            case R.id.test_fast_btn_id:
                //Parcelable
                TestBean bean = new TestBean();
                bean.setId(1L);
                bean.setName("胡涛");
                bean.setAge(30);
                String json = JSON.toJSONString(bean, SerializerFeature.WriteMapNullValue);
                LogUtil.logDebug(TAG, json);
                break;
            case R.id.work_btn_id:
                //测试worker
                startActivity(new Intent(this, WorkManagerControllerActivity.class));
                break;
            case R.id.file_btn_id:
                //文件读写

                break;
            case R.id.view_btn_id:
                //进场动画
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
//                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                Intent intent = new Intent(this, ViewActivity.class);
                startActivity(intent, activityOptionsCompat.toBundle());

//                startActivity(new Intent(this, ViewActivity.class));
                break;
            case R.id.render_btn_id:
                startActivity(new Intent(this, RenderScriptActivity.class));
                break;

            case R.id.share_btn_id:
                Intent contentIntent = new Intent(Intent.ACTION_SEND);
                contentIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                contentIntent.putExtra(Intent.EXTRA_TITLE, "预览");
                contentIntent.setType("text/plain");
                PendingIntent pi = PendingIntent.getBroadcast(this, 0x01,
                        new Intent(this, MyBroadcastReceiver.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                Intent shareIntent = Intent.createChooser(contentIntent, null, pi.getIntentSender());
//                shareIntent.putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS,
//                        new ComponentName[]{new ComponentName("com.github.xiaohu409.androidutildemo", "com.github.xiaohu409.androidutildemo.share.ShareActivity")});

                startActivity(contentIntent);
                break;
            case R.id.print_btn_id:
                Utils.printFilePath(this);
                break;

            case R.id.anim_btn_id:
                startActivity(new Intent(this, AnimActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void bindData() {
        super.bindData();

    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                Log.i("ToVmp","横屏");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                Log.i("ToVmp","竖屏");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
