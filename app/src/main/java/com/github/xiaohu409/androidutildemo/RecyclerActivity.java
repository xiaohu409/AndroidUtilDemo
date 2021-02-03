package com.github.xiaohu409.androidutildemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends  AppCompatActivity {

    private RecyclerAdapter recyclerAdapter;
    public List<TaskBean> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initUI();
        bindData();
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.rv_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        beanList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this, beanList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void bindData() {
        for (TaskBean bean : FileDemoActivity.beanList) {
            beanList.add(bean);
        }
        recyclerAdapter.notifyDataSetChanged();
    }

//    public void addTask(TaskBean taskBean) {
//        beanList.add(taskBean);
//        recyclerAdapter.notifyDataSetChanged();
//    }
}