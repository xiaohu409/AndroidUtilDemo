package com.github.xiaohu409.androidutildemo.bluetooth;

import android.app.Activity;
import android.bluetooth.le.ScanResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.HtRecyclerBaseAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BluetoothListAdapter extends HtRecyclerBaseAdapter<ScanResult, BluetoothListAdapter.Holder> {

    public BluetoothListAdapter(Activity activity, List list) {
        super(activity, list);
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.bluetooth_list_item, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        ScanResult scanResult = getItem(position);
        holder.deviceNameView.setText(String.format("设备名：%s", scanResult.getDevice().getName()));
        holder.macView.setText(String.format("Mac:%s", scanResult.getDevice().getAddress()));
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView deviceNameView;
        private TextView macView;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            deviceNameView = itemView.findViewById(R.id.device_name_tv_id);
            macView = itemView.findViewById(R.id.mac_tv_id);
        }
    }
}
