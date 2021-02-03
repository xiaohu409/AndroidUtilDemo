package com.github.xiaohu409.androidutildemo;

import android.content.Context;
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
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final String TAG = "RecyclerAdapter";

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
        MyDownloadListener myDownloadListener = new MyDownloadListener(taskBean, holder);
        taskBean.setDownloadListener(myDownloadListener);
        Utils.getManager().attachListener(downloadTask, myDownloadListener);
        holder.fileNameView.setText(downloadTask.getFilename());

        if (taskBean.getStatus() == 0) {
            holder.statusView.setText("未下载");
            holder.downloadBtn.setText("开始");
        }
        else if (taskBean.getStatus() == 1) {
            holder.statusView.setText("下载中");
            holder.downloadBtn.setText("暂停");
        }
        else if (taskBean.getStatus() == 2) {
            BreakpointInfo info = downloadTask.getInfo();
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
            holder.statusView.setText("暂停下载");
            holder.downloadBtn.setText("开始");
        }
        else if (taskBean.getStatus() == 3) {
            BreakpointInfo info = downloadTask.getInfo();
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
            holder.statusView.setText("下载完成");
            holder.downloadBtn.setText("打开");
        }
        else if (taskBean.getStatus() == 4) {
            BreakpointInfo info = downloadTask.getInfo();
            Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
            holder.statusView.setText("报错");
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

    private class DownloadClick implements View.OnClickListener {

        private TaskBean taskBean;
        private Holder holder;

        public DownloadClick(TaskBean taskBean, Holder holder) {
            this.taskBean = taskBean;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (taskBean.getStatus() == 0) {
                //开始下载
                if (taskBean.getDownloadTask() != null) {
                    Utils.getManager().enqueueTaskWithUnifiedListener(taskBean.getDownloadTask(), taskBean.getDownloadListener());
                }
                taskBean.setStatus(1);
                holder.statusView.setText("下载中");
                holder.downloadBtn.setText("暂停");
            } else if (taskBean.getStatus() == 1) {
                //下载中
                if (taskBean.getDownloadTask() != null) {
                    taskBean.getDownloadTask().cancel();
                }
                taskBean.setStatus(2);
                holder.statusView.setText("暂停中");
                holder.downloadBtn.setText("开始");
            } else if (taskBean.getStatus() == 2) {
                //继续下载
                if (taskBean.getDownloadTask() != null) {
                    Utils.getManager().enqueueTaskWithUnifiedListener(taskBean.getDownloadTask(), taskBean.getDownloadListener());
                }
                taskBean.setStatus(1);
                holder.statusView.setText("下载中");
                holder.downloadBtn.setText("暂停");
            } else if (taskBean.getStatus() == 3) {
                holder.statusView.setText("完成");
                holder.downloadBtn.setText("打开");
            }
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

        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            LogUtil.logDebug(TAG, "totalLength" + totalLength);
            holder.progressBar.setMax((int) totalLength);

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            LogUtil.logDebug(TAG, "currentOffset" + currentOffset);
            LogUtil.logDebug(TAG, "totalLength" + totalLength);
            Utils.calcProgressToView(holder.progressBar, currentOffset, totalLength);
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
            ToastUtil.showShort("任务结束了：" + cause.name());
            if (cause == EndCause.COMPLETED) {
                taskBean.setStatus(3);
                holder.statusView.setText("完成");
                holder.downloadBtn.setText("打开");
            }
            else {
//                    itemInfo.status = 2; //修改状态
//                    if (cause == EndCause.CANCELED) {
//                        Toast.makeText(context, "任务取消", Toast.LENGTH_SHORT).show();
//                    } else if (cause == EndCause.ERROR) {
//                        Log.i("bqt", "【任务出错】");
//                    } else if (cause == EndCause.FILE_BUSY || cause == EndCause.SAME_TASK_BUSY || cause == EndCause.PRE_ALLOCATE_FAILED) {
//                        Log.i("bqt", "【taskEnd】" + cause.name());
//                    }
            }
        }
    }

}
