package com.github.xiaohu409.androidutildemo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileDemoActivity extends BaseUIActivity {

    private Button downloadBtn;
    private ProgressBar progressBar;
    private Button downloadBtn1;
    private ProgressBar progressBar1;
    private DownloadTask task;
    private StatusUtil.Status status;
    public static List<TaskBean> beanList = new ArrayList<>();
    private TextView statusView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_file_demo;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void initUI() {
        downloadBtn = findViewById(R.id.test_file_btn_id);
        downloadBtn.setOnClickListener(this);
        statusView = findViewById(R.id.status_id);
        progressBar = findViewById(R.id.progressBar);
//        status = StatusUtil.getStatus(task);
//        if (status == StatusUtil.Status.COMPLETED) {
//            progressBar.setProgress(progressBar.getMax());
//        }
        downloadBtn1 = findViewById(R.id.test_file_btn_id1);
        downloadBtn1.setOnClickListener(this);
        progressBar1 = findViewById(R.id.progressBar1);

        Button linkBtn = findViewById(R.id.link_btn_id);
        linkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.test_file_btn_id:
                download(beanList.get(0));
                break;
            case R.id.test_file_btn_id1:
                download1();
                break;
            case R.id.link_btn_id:
                Intent intent = new Intent(this, RecyclerActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void bindData() {

        // cancel
        //task.cancel();
        // execute task synchronized
        //task.execute(listener);
        String url = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";
        String fileName = Utils.getFileName(url);
        TaskBean taskBean = new TaskBean();
        taskBean.setFileNameUrl(url);
        taskBean.setFileName(fileName);
        beanList.add(taskBean);

    }

    private void download(TaskBean taskBean) {
        HtDownloadListener htDownloadListener = createDownloadListener(taskBean);
        taskBean.setHtDownloadListener(htDownloadListener);
        if (taskBean.getStatus() == 0) {
            //开始下载
            if (taskBean.getDownloadTask() == null) {
                task = createDownloadTask(taskBean);
                task.enqueue(htDownloadListener);
                taskBean.setDownloadTask(task);
            }
            taskBean.setStatus(1);
            statusView.setText("下载中");
            downloadBtn.setText("暂停");
        } else if (taskBean.getStatus() == 1) {
            //下载中
            if (taskBean.getDownloadTask() != null) {
                taskBean.getDownloadTask().cancel();
            }
            taskBean.setStatus(2);
            statusView.setText("暂停中");
            downloadBtn.setText("开始");
        } else if (taskBean.getStatus() == 2) {
            //继续下载
            if (taskBean.getDownloadTask() != null) {
                taskBean.getDownloadTask().enqueue(htDownloadListener);
            }
            taskBean.setStatus(1);
            statusView.setText("下载中");
            downloadBtn.setText("暂停");
        } else if (taskBean.getStatus() == 3) {
            statusView.setText("完成");
            downloadBtn.setText("打开");
        }

    }

    private void download1() {
//        HtDownloadListener downloadListener = new HtDownloadListener(progressBar1,);
//        String filename = "dongpan";
//        String url = "https://downapp.baidu.com/appsearch/AndroidPhone/1.0.78.155/1/1012271b/20190404124002/appsearch_AndroidPhone_1-0-78-155_1012271b.apk";
//        File parentFile = Utils.getParentFile(this);
//        task = new DownloadTask.Builder(url, parentFile)
//                .setFilename(filename)
//                // the minimal interval millisecond for callback progress
//                .setMinIntervalMillisCallbackProcess(30)
//                // do re-download even if the task has already been completed in the past.
//                .setPassIfAlreadyCompleted(false)
//                .build();
//        task.enqueue(downloadListener);
//
//        TaskBean taskBean = new TaskBean();
//        taskBean.setDownloadTask(task);
//        beanList.add(taskBean);
    }

    /**
     * 创建DownloadTask
     * @param taskBean
     * @return
     */
    private DownloadTask createDownloadTask(TaskBean taskBean) {
        File parentFile = Utils.getParentFile(this);
        task = new DownloadTask.Builder(taskBean.getFileNameUrl(), parentFile)
                .setFilename(taskBean.getFileName())
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(30)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();
        return task;
    }

    private HtDownloadListener createDownloadListener(TaskBean taskBean) {
        HtDownloadListener htDownloadListener = new HtDownloadListener(progressBar);
//        htDownloadListener.setEndCallback(new HtDownloadListener.EndCallback() {
//            @Override
//            public void onEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
//                if (cause == EndCause.COMPLETED) {
//                    taskBean.setStatus(3);
//                    statusView.setText("完成");
//                    downloadBtn.setText("打开");
//                } else {
////                    itemInfo.status = 2; //修改状态
////                    if (cause == EndCause.CANCELED) {
////                        Toast.makeText(context, "任务取消", Toast.LENGTH_SHORT).show();
////                    } else if (cause == EndCause.ERROR) {
////                        Log.i("bqt", "【任务出错】");
////                    } else if (cause == EndCause.FILE_BUSY || cause == EndCause.SAME_TASK_BUSY || cause == EndCause.PRE_ALLOCATE_FAILED) {
////                        Log.i("bqt", "【taskEnd】" + cause.name());
////                    }
//                }
//            }
//        });
        return htDownloadListener;
    }

}