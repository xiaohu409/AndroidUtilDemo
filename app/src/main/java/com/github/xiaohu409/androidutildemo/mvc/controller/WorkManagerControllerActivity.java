package com.github.xiaohu409.androidutildemo.mvc.controller;

import android.view.View;
import android.widget.TextView;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.mvc.work.UploadWorker;

import butterknife.BindView;

public class WorkManagerControllerActivity extends BaseUIActivity {

    @BindView(R.id.task_status_id)
    TextView taskStatusView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_manager_controller;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void bindData() {
        runWork();
    }

    private void runWork() {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest
                .Builder(UploadWorker.class)
                .build();
        WorkManager.getInstance(this).enqueue(workRequest);
//        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(WorkInfo workInfo) {
//                if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//                    taskStatusView.setText("执行成功");
//                }
//            }
//        });


        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    taskStatusView.setText("执行成功");
                }
        });

    }

}
