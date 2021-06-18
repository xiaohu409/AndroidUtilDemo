package com.github.xiaohu409.androidutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：BluetoothDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2019/8/12
 * 文件版本：1.0
 */
public class BluetoothUtil {

    private static final String TAG = "BluetoothUtil";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothUtilCallback bluetoothUtilCallback;
    private Handler handler;
    private boolean enable;
    private Context context;
    private BluetoothLeScanner scanner;
    private int count = 0;
    private static final int scanTime = 5000;

    /**
     * 构造方法
     * @param context
     */
    public BluetoothUtil(Context context) {
        this.context = context;
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        context.registerReceiver(receiver, filter);
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.showShort("不支持低功耗蓝牙");
        }
        else {
            BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = manager.getAdapter();
            enable = bluetoothAdapter.isEnabled();
            handler = new Handler();
        }
    }

    /**
     * 打开蓝牙
     */
    public void openBluetooth() {
        if (bluetoothAdapter != null && !enable) {
//            bluetoothAdapter.enable();
            LogUtil.logDebug(TAG, "打开蓝牙");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(enableBtIntent);
        }
        if (enable) {
            LogUtil.logDebug(TAG, "蓝牙已经打开了");
            startDiscoverBluetooth();
        }

    }

    /**
     * 开始扫描
     */
    public void startDiscoverBluetooth() {
        if (count != 0) {
            return;
        }
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        if (scanner == null) {
            LogUtil.logDebug(TAG, "is null");
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                bluetoothAdapter.stopLeScan(leScanCallback)
                scanner.stopScan(leScanCallback);
                bluetoothUtilCallback.onStop();
                count = 0;
            }
        }, scanTime);
//        bluetoothAdapter.startLeScan(leScanCallback);
        scanner.startScan(leScanCallback);
        count++;
        LogUtil.logDebug(TAG, "扫描开始" + count);
    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
    }

    public void releaseReceiver() {
        context.unregisterReceiver(receiver);
    }

    /**
     * 扫描回调
     */
//    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
//        @Override
//        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//            if (bluetoothUtilCallback != null) {
//                bluetoothUtilCallback.onScanDevice(device);
//            }
//        }
//
//    };

    private ScanCallback leScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            if (bluetoothUtilCallback != null) {
                bluetoothUtilCallback.onScanDevice(result);
            }
            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            if (bluetoothUtilCallback != null) {
                bluetoothUtilCallback.onScanDevice(results);
            }
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            if (bluetoothUtilCallback != null) {
                bluetoothUtilCallback.onScanFail(errorCode);
            }
            super.onScanFailed(errorCode);

        }
    };

    /**
     * 回调方法
     */
    public interface BluetoothUtilCallback {
//        void onScanDevice(BluetoothDevice device);
        void onScanDevice(ScanResult scanResult);
        void onScanDevice(List<ScanResult> results);
        void onScanFail(int errorCode);
        void onStop();
    }

    /**
     * 设置回调
     * @param bluetoothUtilCallback
     */
    public void setBluetoothUtilCallback(BluetoothUtilCallback bluetoothUtilCallback) {
        this.bluetoothUtilCallback = bluetoothUtilCallback;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        //to check if BluetoothAdapter is enable by your code
                        enable = true;
                        startDiscoverBluetooth();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };
}
