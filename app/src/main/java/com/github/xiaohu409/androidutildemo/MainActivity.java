package com.github.xiaohu409.androidutildemo;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.xiaohu409.androidutil.BluetoothUtil;
import com.github.xiaohu409.androidutil.DateTimeUtil;
import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.SharePreUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.bean.TestBean;
import com.github.xiaohu409.androidutildemo.databinding.ActivityMainBinding;
import com.github.xiaohu409.androidutildemo.mvc.controller.LoginControllerActivity;
import com.github.xiaohu409.androidutildemo.mvc.controller.WorkManagerControllerActivity;

import com.github.xiaohu409.androidutildemo.widget.HtEditText;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    private ActivityMainBinding activityMainBinding;
    private FrameLayout flVideoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        A a = new A();
        getLifecycle().addObserver(a);
    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public View getView() {
//        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
//        return activityMainBinding.getRoot();
//    }


    public void initUI() {
////        Button utilBtn = findViewById(R.id.test_util_btn_id);
//        activityMainBinding.testUtilBtnId.setOnClickListener(this);
//        Button bluetoothBtn = findViewById(R.id.bluetooth_btn_id);
//        bluetoothBtn.setOnClickListener(this);
////        Button loginBtn = findViewById(R.id.login_btn_id);
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
        flVideoContainer = findViewById(R.id.flVideoContainer);
        WebView webView = findViewById(R.id.web_view_id);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            WebChromeClient.CustomViewCallback mCallback;
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {

                fullScreen();

                webView.setVisibility(View.GONE);
                flVideoContainer.setVisibility(View.VISIBLE);
                flVideoContainer.addView(view);
                mCallback = callback;
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {

                fullScreen();

                webView.setVisibility(View.VISIBLE);
                flVideoContainer.setVisibility(View.GONE);
                flVideoContainer.removeAllViews();
                super.onHideCustomView();

            }
        });




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
        webView.loadUrl("https://www.baidu.com");
//        String s = "hutao";
//        String pass = "EBD0B5D2-F4E7-453A-9E16-01AE8F6E2977";
//        String encoded = encrypt(s, pass);
//        //System.out.println("加密之前：{}" + s);
//        System.out.println("加密结果：{}" + encoded);
//        //decrypt(encoded, pass);
//        System.out.println("解密结果：{}" + decrypt(encoded, pass));

        HtEditText editText = findViewById(R.id.input_et_id);
        editText.setEditTextCallback((String s) -> ToastUtil.showShort(s));

        Button intentBtn = findViewById(R.id.intent_btn_id);
        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("hutao");
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.test_util_btn_id:
//                //        ToastUtil.showLong("显示");
//                SharePreUtil.getInstance().put("name", "胡涛");
//                SharePreUtil.getInstance().put("age", 22);
//                SharePreUtil.getInstance().put("height", 1.75);
//                SharePreUtil.getInstance().put("man", true);
//                ToastUtil.showLong(SharePreUtil.getInstance().getString("name"));
//                LogUtil.logDebug(TAG, DateTimeUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
//                break;
//            case R.id.bluetooth_btn_id:
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                    requestPermissions(new String[] {
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.BLUETOOTH,
//                            Manifest.permission.BLUETOOTH_ADMIN
//                    }, REQUEST_PERMISSION_BLUETOOTH);
//                }
//                else {
//                    scanBluetooth();
//                }
//                break;
//            case R.id.login_btn_id:
//                //测试登录
//
//                startActivity(new Intent(this, LoginControllerActivity.class));
//                break;
//            case R.id.test_fast_btn_id:
//                //Parcelable
//                TestBean bean = new TestBean();
//                bean.setId(1L);
//                bean.setName("胡涛");
//                bean.setAge(30);
//                String json = JSON.toJSONString(bean, SerializerFeature.WriteMapNullValue);
//                LogUtil.logDebug(TAG, json);
//                break;
//            case R.id.work_btn_id:
//                //测试worker
//                startActivity(new Intent(this, WorkManagerControllerActivity.class));
//                break;
//            case R.id.file_btn_id:
//                //文件读写
//
//                break;
//        }
//    }
//
//    @Override
//    public void grantedPermission(int type) {
//        super.grantedPermission(type);
//        scanBluetooth();
//    }
//
//    @Override
//    public void deniedPermission(int type) {
//        super.deniedPermission(type);
//        ToastUtil.showShort("你拒绝了此权限");
//    }



    /**
     * 扫码蓝牙
     */
    private void scanBluetooth() {
        final Set<BluetoothDevice> deviceSet = new HashSet<>();
        BluetoothUtil bluetoothUtil = new BluetoothUtil(this);
        //设置蓝牙扫描回调方法
        bluetoothUtil.setBluetoothUtilCallback(new BluetoothUtil.BluetoothUtilCallback() {
            @Override
            public void onScanDevice(BluetoothDevice device) {
//                Log.d(TAG, device.getName() + ":" + device.getAddress());
                deviceSet.add(device);
            }

            @Override
            public void onStop() {
                System.out.println(deviceSet);
            }
        });
        //打开蓝牙
        bluetoothUtil.openBluetooth();
        //开始扫描
        bluetoothUtil.startDiscoverBluetooth();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//    @Override
//    public void bindData() {
//        super.bindData();
//    }

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
}
