package com.github.xiaohu409.androidutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

/**
 * 项目名称：BluetoothDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/8/12
 * 文件版本：1.0
 */
public class BluetoothUtil {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothUtilCallback bluetoothUtilCallback;
    private Handler handler;

    public BluetoothUtil(Context context) {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.showShort("不支持低功耗蓝牙");
        }
        else {
            BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = manager.getAdapter();
            handler = new Handler();
        }
    }

    public void openBluetooth() {
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    public void startDiscoverBluetooth() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothAdapter.stopLeScan(leScanCallback);
                bluetoothUtilCallback.onStop();
            }
        }, 1000);
        bluetoothAdapter.startLeScan(leScanCallback);
    }

    public void closeBluetooth() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (bluetoothUtilCallback != null) {
                bluetoothUtilCallback.onScanDevice(device);
            }
        }
    };

    public interface BluetoothUtilCallback {
        void onScanDevice(BluetoothDevice device);
        void onStop();
    }

    public void setBluetoothUtilCallback(BluetoothUtilCallback bluetoothUtilCallback) {
        this.bluetoothUtilCallback = bluetoothUtilCallback;
    }
}