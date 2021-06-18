package com.github.xiaohu409.androidutildemo.bluetooth;


import android.Manifest;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaohu409.androidutil.BluetoothUtil;
import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BluetoothListActivity extends BaseUIActivity {

    private static final String TAG = "BluetoothListActivity";
    private BluetoothUtil bluetoothUtil;
    private List<ScanResult> scanResultList;
    private BluetoothListAdapter listAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth_list;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void initUI() {
        super.initUI();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("蓝牙");
        //自定义
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(R.layout.title_bar);
//        TextView titleView  = actionBar.getCustomView().findViewById(R.id.title_tv_id);
//        titleView.setText("标题");
        RecyclerView recyclerView = findViewById(R.id.rv_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scanResultList = new ArrayList<>();
        listAdapter = new BluetoothListAdapter(this, scanResultList);
        recyclerView.setAdapter(listAdapter);
        FloatingActionButton actionButton = findViewById(R.id.float_scan_btn_id);
        actionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.float_scan_btn_id:
                ToastUtil.showShort("扫描");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
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
    public void bindData() {
        super.bindData();
        bluetoothUtil = new BluetoothUtil(this);
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
        final Set<ScanResult> scanResults = new HashSet<>();
        //设置蓝牙扫描回调方法
//        bluetoothUtil.setBluetoothUtilCallback(new BluetoothUtil.BluetoothUtilCallback() {
//            @Override
//            public void onScanDevice(BluetoothDevice device) {
////                Log.d(TAG, device.getName() + ":" + device.getAddress());
//                deviceSet.add(device);
//            }
//
//            @Override
//            public void onStop() {
//                System.out.println(deviceSet);
//            }
//        });
        bluetoothUtil.setBluetoothUtilCallback(new BluetoothUtil.BluetoothUtilCallback() {

            @Override
            public void onScanDevice(ScanResult scanResult) {
                scanResults.add(scanResult);

                LogUtil.logDebug(TAG, scanResult.toString());
            }

            @Override
            public void onScanDevice(List<ScanResult> results) {
//                LogUtil.logDebug(TAG, results.toString());
            }

            @Override
            public void onScanFail(int errorCode) {
                LogUtil.logDebug(TAG, errorCode + "");
            }

            @Override
            public void onStop() {
                scanResultList.clear();
                scanResultList.addAll(scanResults);
                listAdapter.notifyDataSetChanged();
            }
        });
        //打开蓝牙
        bluetoothUtil.openBluetooth();
        //开始扫描
//        bluetoothUtil.startDiscoverBluetooth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothUtil.releaseReceiver();
    }
}