package com.github.xiaohu409.androidutildemo;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDemoActivity extends BaseUIActivity {

    private Button downloadBtn;
    private ProgressBar progressBar;
    private Button downloadBtn1;
    private ProgressBar progressBar1;
    private DownloadTask task;
    private StatusUtil.Status status;
    public static List<TaskBean> beanList = new ArrayList<>();
    private TextView statusView;
    private TextView statusView1;

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
        statusView1 = findViewById(R.id.status_id1);
        progressBar1 = findViewById(R.id.progressBar1);

        Button batchBtn = findViewById(R.id.batch_download_id);
        batchBtn.setOnClickListener(this);
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
                download1(beanList.get(1));
                break;
            case R.id.batch_download_id:
                batchDownload();
                break;
            case R.id.link_btn_id:
                Intent intent = new Intent(this, RecyclerActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void bindData() {
        requestPermission();
//        DownloadDispatcher.setMaxParallelRunningCount(10);
        List<String> urlList =  Arrays.asList(
                "http://dldir1.qq.com/weixin/android/weixin6516android1120.apk",
                "http://192.168.31.150:8080/downloadTest?fileName=app-debug.apk",
                "http://192.168.31.150:8080/downloadTest?fileName=demo.zip",
                "http://192.168.31.150:8080/downloadTest?fileName=image1882813695064063741.png",
                "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk"
        );
        for (String url : urlList) {
            String fileName = Utils.getFileName(url);
            TaskBean taskBean = new TaskBean();
            taskBean.setFileNameUrl(url);
            taskBean.setFileName(fileName);
            beanList.add(taskBean);
        }
    }

    private void download(TaskBean taskBean) {
        DownloadListener downloadListener1 = createDownloadListener1();
//        DownloadListener downloadListener2 = createDownloadListener2();
        if (taskBean.getStatus() == 0) {
            //开始下载
            if (taskBean.getDownloadTask() == null) {
                task = createDownloadTask(taskBean);
                taskBean.setDownloadTask(task);
                Utils.getManager().enqueueTaskWithUnifiedListener(task, downloadListener1);
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
                Utils.getManager().enqueueTaskWithUnifiedListener(task, downloadListener1);
            }
            taskBean.setStatus(1);
            statusView.setText("下载中");
            downloadBtn.setText("暂停");
        } else if (taskBean.getStatus() == 3) {
            statusView.setText("完成");
            downloadBtn.setText("打开");
        }

    }

    private void download1(TaskBean taskBean) {
        DownloadListener downloadListener2 = createDownloadListener2();
        if (taskBean.getStatus() == 0) {
            //开始下载
            if (taskBean.getDownloadTask() == null) {
                task = createDownloadTask(taskBean);
                taskBean.setDownloadTask(task);
                Utils.getManager().enqueueTaskWithUnifiedListener(task, downloadListener2);
            }
            taskBean.setStatus(1);
            statusView1.setText("下载中");
            downloadBtn1.setText("暂停");
        } else if (taskBean.getStatus() == 1) {
            //下载中
            if (taskBean.getDownloadTask() != null) {
                taskBean.getDownloadTask().cancel();
            }
            taskBean.setStatus(2);
            statusView1.setText("暂停中");
            downloadBtn1.setText("开始");
        } else if (taskBean.getStatus() == 2) {
            //继续下载
            if (taskBean.getDownloadTask() != null) {
                Utils.getManager().enqueueTaskWithUnifiedListener(task, downloadListener2);
            }
            taskBean.setStatus(1);
            statusView1.setText("下载中");
            downloadBtn1.setText("暂停");
        } else if (taskBean.getStatus() == 3) {
            statusView1.setText("完成");
            downloadBtn1.setText("打开");
        }
    }

    /**
     * 批零下载
     */
    private void batchDownload() {
        for (TaskBean taskBean : beanList) {
            task = createDownloadTask(taskBean);
            taskBean.setDownloadTask(task);
            Utils.getManager().enqueueTaskWithUnifiedListener(task, createDownloadListener1());
        }
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
                .setMinIntervalMillisCallbackProcess(1)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .setAutoCallbackToUIThread(true)
                .build();
        return task;
    }

    private DownloadListener createDownloadListener1() {
        return new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {

            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
                progressBar.setMax((int) totalLength);
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                Utils.calcProgressToView(progressBar, currentOffset, totalLength);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {

            }
        };
    }

    private DownloadListener createDownloadListener2() {
        return new DownloadListener2() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {

            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {

            }
        };
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    requestPermissions(new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.REQUEST_INSTALL_PACKAGES
                    }, REQUEST_PERMISSION_BLUETOOTH);
                }
    }

    @Override
    public void grantedPermission(int type) {
        super.grantedPermission(type);
    }

    @Override
    public void deniedPermission(int type) {
        super.deniedPermission(type);
    }
}