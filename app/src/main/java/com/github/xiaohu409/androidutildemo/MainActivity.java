package com.github.xiaohu409.androidutildemo;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.xiaohu409.androidutil.BluetoothUtil;
import com.github.xiaohu409.androidutil.DateTimeUtil;
import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.SharePreUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.base.BaseActivity;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        Button utilBtn = findViewById(R.id.test_util_btn_id);
        utilBtn.setOnClickListener(this);
        Button bluetoothBtn = findViewById(R.id.bluetooth_btn_id);
        bluetoothBtn.setOnClickListener(this);
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

    private void scanBluetooth() {
        final Set<BluetoothDevice> deviceSet = new HashSet<>();
        BluetoothUtil bluetoothUtil = new BluetoothUtil(this);
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
        bluetoothUtil.openBluetooth();
        bluetoothUtil.startDiscoverBluetooth();
    }
}
