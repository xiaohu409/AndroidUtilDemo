package com.github.xiaohu409.androidutildemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutil.ToastUtil;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final String TAG = "RecyclerAdapter";

    private Context context;
    private List<TaskBean> beanList;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<TaskBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TaskBean taskBean = getItem(position);
        DownloadTask downloadTask = taskBean.getDownloadTask();
        DownloadListener downloadListener = taskBean.getDownloadListener();
        if (downloadTask != null && downloadListener == null) {
            MyDownloadListener myDownloadListener = new MyDownloadListener(taskBean, holder);
            taskBean.setDownloadListener(myDownloadListener);
            Utils.getManager().attachListener(downloadTask, myDownloadListener);
        }
        holder.fileNameView.setText(taskBean.getFileName());
        StatusUtil.Status status = StatusUtil.getStatus(taskBean.getFileNameUrl(), Utils.getParentFile(context).getPath(), taskBean.getFileName());
        if (status == StatusUtil.Status.UNKNOWN) {
            taskBean.setStatus(0);
            holder.progressBar.setProgress(0);
        }
        else if (status == StatusUtil.Status.RUNNING) {
            taskBean.setStatus(1);
            BreakpointInfo info = StatusUtil.getCurrentInfo(taskBean.getFileNameUrl(), Utils.getParentFile(context).getPath(), taskBean.getFileName());
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
        }
        else if (status == StatusUtil.Status.IDLE) {
            taskBean.setStatus(2);
            BreakpointInfo info = StatusUtil.getCurrentInfo(taskBean.getFileNameUrl(), Utils.getParentFile(context).getPath(), taskBean.getFileName());
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
        }
        else if (status == StatusUtil.Status.COMPLETED) {
            taskBean.setStatus(3);
            holder.progressBar.setProgress(holder.progressBar.getMax());
        }
        else {
            holder.progressBar.setProgress(0);
        }
        if (taskBean.getStatus() == 0) {
            holder.statusView.setText("未下载");
            holder.downloadBtn.setText("开始");
        }
        else if (taskBean.getStatus() == 1) {
            holder.statusView.setText("下载中");
            holder.downloadBtn.setText("暂停");
        }
        else if (taskBean.getStatus() == 2) {
            holder.statusView.setText("暂停下载");
            holder.downloadBtn.setText("开始");
        }
        else if (taskBean.getStatus() == 3) {
            holder.statusView.setText("下载完成");
            holder.downloadBtn.setText("打开");
        }
        else if (taskBean.getStatus() == 4) {
            holder.statusView.setText("下载报错");
            holder.downloadBtn.setText("重新开始");
        }

        holder.downloadBtn.setOnClickListener(new DownloadClick(taskBean, holder));
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public TaskBean getItem(int position) {
        return beanList.get(position);
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView fileNameView;
        private TextView statusView;
        private Button downloadBtn;
        private ProgressBar progressBar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            fileNameView = itemView.findViewById(R.id.file_name_id);
            statusView = itemView.findViewById(R.id.status_id);
            downloadBtn = itemView.findViewById(R.id.btn_id);
            progressBar = itemView.findViewById(R.id.progressbar_id);
        }
    }

    private class DownloadClick implements View.OnClickListener {

        private TaskBean taskBean;
        private Holder holder;

        public DownloadClick(TaskBean taskBean, Holder holder) {
            this.taskBean = taskBean;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (taskBean.getDownloadTask() == null) {
                DownloadTask downloadTask = Utils.createDownloadTask(context, taskBean);
                taskBean.setDownloadTask(downloadTask);
            }
            if (taskBean.getDownloadListener() == null) {
                MyDownloadListener myDownloadListener = new MyDownloadListener(taskBean, holder);
                taskBean.setDownloadListener(myDownloadListener);
            }
            if (taskBean.getStatus() == 0) {
                //开始下载
                Utils.getManager().enqueueTaskWithUnifiedListener(taskBean.getDownloadTask(), taskBean.getDownloadListener());
                taskBean.setStatus(1);
                holder.statusView.setText("下载中");
                holder.downloadBtn.setText("暂停");
            }
            else if (taskBean.getStatus() == 1) {
                //下载中
                if (taskBean.getDownloadTask() != null) {
                    taskBean.getDownloadTask().cancel();
                }
                taskBean.setStatus(2);
                holder.statusView.setText("暂停中");
                holder.downloadBtn.setText("开始");
            }
            else if (taskBean.getStatus() == 2) {
                //继续下载
                Utils.getManager().enqueueTaskWithUnifiedListener(taskBean.getDownloadTask(), taskBean.getDownloadListener());
                taskBean.setStatus(1);
                holder.statusView.setText("下载中");
                holder.downloadBtn.setText("暂停");
            }
            else if (taskBean.getStatus() == 3) {
                holder.statusView.setText("下载完成");
                holder.downloadBtn.setText("打开");
                OpenFileUtil.openFile(context, taskBean.getDownloadTask().getFile().getPath());
            }
            else if (taskBean.getStatus() == 4) {
                //出错了 继续下载
                Utils.getManager().enqueueTaskWithUnifiedListener(taskBean.getDownloadTask(), taskBean.getDownloadListener());
                taskBean.setStatus(1);
                holder.statusView.setText("下载中");
                holder.downloadBtn.setText("暂停");
            }
            else if (taskBean.getStatus() == 5) {
                //取消了 继续下载
                Utils.getManager().enqueueTaskWithUnifiedListener(taskBean.getDownloadTask(), taskBean.getDownloadListener());
                taskBean.setStatus(1);
                holder.statusView.setText("下载中");
                holder.downloadBtn.setText("暂停");
            }
            BreakpointInfo info = StatusUtil.getCurrentInfo(taskBean.getFileNameUrl(), Utils.getParentFile(context).getPath(), taskBean.getFileName());
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
        }
    }

    private class MyDownloadListener extends DownloadListener1 {

        private TaskBean taskBean;
        private Holder holder;

        public MyDownloadListener(TaskBean taskBean, Holder holder) {
            this.taskBean = taskBean;
            this.holder = holder;
        }

        @Override
        public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
            LogUtil.logDebug(TAG, "taskStart:" + task.getFilename());
        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            LogUtil.logDebug(TAG, "retry:" + task.getFilename());
        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            LogUtil.logDebug(TAG, "connected: " + task.getFilename() + "totalLength" + totalLength);
            holder.progressBar.setMax((int) totalLength);

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            LogUtil.logDebug(TAG, "progress:" + task.getFilename() + "currentOffset" + currentOffset);
//            LogUtil.logDebug(TAG, "totalLength" + totalLength);
            Utils.calcProgressToView(holder.progressBar, currentOffset, totalLength);
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
            LogUtil.logDebug(TAG, "taskEnd:" + task.getFilename() + "cause" + cause.name());
            if (cause == EndCause.COMPLETED) {
                taskBean.setStatus(3);
                holder.statusView.setText("下载完成");
                holder.downloadBtn.setText("打开");
            }
            else if (cause == EndCause.CANCELED) {
                taskBean.setStatus(2);
//                holder.statusView.setText("暂停了");
            }
            else if (cause == EndCause.ERROR) {
                taskBean.setStatus(3);
                holder.statusView.setText("下载完成");
                holder.downloadBtn.setText("打开");

            }
            else if (cause == EndCause.FILE_BUSY || cause == EndCause.SAME_TASK_BUSY || cause == EndCause.PRE_ALLOCATE_FAILED) {
                taskBean.setStatus(6);
//                holder.statusView.setText("其他异常");
            }
            BreakpointInfo info = StatusUtil.getCurrentInfo(taskBean.getFileNameUrl(), Utils.getParentFile(context).getPath(), taskBean.getFileName());
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
        }
    }

}
