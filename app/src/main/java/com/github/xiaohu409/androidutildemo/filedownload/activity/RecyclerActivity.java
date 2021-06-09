package com.github.xiaohu409.androidutildemo.filedownload.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.filedownload.RecyclerAdapter;
import com.github.xiaohu409.androidutildemo.filedownload.TaskBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends BaseUIActivity {

    private RecyclerAdapter recyclerAdapter;
    public List<TaskBean> beanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void initUI() {
        RecyclerView recyclerView = findViewById(R.id.rv_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHorizontalScrollBarEnabled(true);
        beanList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this, beanList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void bindData() {
        for (TaskBean bean : FileDemoActivity.beanList) {
            beanList.add(bean);
        }
        recyclerAdapter.notifyDataSetChanged();
    }
}