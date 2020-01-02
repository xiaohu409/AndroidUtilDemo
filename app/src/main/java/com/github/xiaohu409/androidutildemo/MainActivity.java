package com.github.xiaohu409.androidutildemo;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.xiaohu409.androidutil.BluetoothUtil;
import com.github.xiaohu409.androidutil.DateTimeUtil;
import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.SharePreUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.bean.TestBean;
import com.github.xiaohu409.androidutildemo.mvc.controller.LoginControllerActivity;
import com.github.xiaohu409.androidutildemo.mvc.controller.WorkManagerControllerActivity;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseUIActivity {

    private static final String TAG = "MainActivity";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        initUI();
//        A a = new A();
//        getLifecycle().addObserver(a);
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        Button utilBtn = findViewById(R.id.test_util_btn_id);
        utilBtn.setOnClickListener(this);
        Button bluetoothBtn = findViewById(R.id.bluetooth_btn_id);
        bluetoothBtn.setOnClickListener(this);
        Button loginBtn = findViewById(R.id.login_btn_id);
        loginBtn.setOnClickListener(this);
        Button testFastJsonBtn = findViewById(R.id.test_fast_btn_id);
        testFastJsonBtn.setOnClickListener(this);

        Button workMangagerBtn = findViewById(R.id.work_btn_id);
        workMangagerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_util_btn_id:
                //        ToastUtil.showLong("显示");
                SharePreUtil.getInstance().put("name", "胡涛");
                SharePreUtil.getInstance().put("age", 22);
                SharePreUtil.getInstance().put("height", 1.75);
                SharePreUtil.getInstance().put("man", true);
                ToastUtil.showLong(SharePreUtil.getInstance().getString("name"));
                LogUtil.logDebug(TAG, DateTimeUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
                break;
            case R.id.bluetooth_btn_id:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    requestPermissions(new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN
                    }, REQUEST_PERMISSION_BLUETOOTH);
                }
                else {
                    scanBluetooth();
                }
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
        }
    }

    @Override
    public void grantedPermission(int type) {
        super.grantedPermission(type);
        scanBluetooth();
    }

    @Override
    public void deniedPermission(int type) {
        super.deniedPermission(type);
        ToastUtil.showShort("你拒绝了此权限");
    }

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

    @Override
    public void bindData() {
        super.bindData();
    }
}
