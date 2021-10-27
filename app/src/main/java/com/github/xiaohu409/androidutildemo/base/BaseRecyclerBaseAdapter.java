/*
 * Copyright (c) 2020.
 */

package com.github.xiaohu409.androidutildemo.base;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class BaseRecyclerBaseAdapter<DT ,VT extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VT> {

    protected Activity activity;
    protected List<DT> list;
    protected LayoutInflater layoutInflater;
    private ItemClickCallback<DT> itemClickCallback;

    public BaseRecyclerBaseAdapter(Activity activity, List<DT> list) {
        this.activity = activity;
        this.list = list;
//        layoutInflater = LayoutInflater.from(activity);
        layoutInflater = LayoutInflater.from(App.getInstance());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public DT getItem(int position) {
        return list.get(position);
    }

    public class ItemClick implements View.OnClickListener {

        DT t;

        public ItemClick(DT t) {
            this.t = t;
        }

        @Override
        public void onClick(View v) {
            if (itemClickCallback != null) {
                itemClickCallback.onItemClick(t);
            }
        }
    }

    public interface ItemClickCallback<DT> {
        void onItemClick(DT t);
    }

    public void setItemClickCallback(ItemClickCallback<DT> itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }
}
