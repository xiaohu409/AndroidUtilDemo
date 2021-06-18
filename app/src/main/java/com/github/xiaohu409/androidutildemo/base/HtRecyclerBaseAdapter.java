/*
 * Copyright (c) 2020.
 */

package com.github.xiaohu409.androidutildemo.base;

import android.app.Activity;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class HtRecyclerBaseAdapter<DT ,VT extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VT> {

    protected Activity activity;
    protected List<DT> list;
    protected LayoutInflater layoutInflater;

    public HtRecyclerBaseAdapter(Activity activity, List<DT> list) {
        this.activity = activity;
        this.list = list;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public DT getItem(int position) {
        return list.get(position);
    }
}
