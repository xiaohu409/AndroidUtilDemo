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

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.util.List;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

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
        holder.fileNameView.setText(downloadTask.getFilename());
        taskBean.getHtDownloadListener().setProgressBar(holder.progressBar);
        taskBean.getHtDownloadListener().setEndCallback(new MyHtDownloadListener(taskBean, holder));
        holder.downloadBtn.setOnClickListener(new DownloadClick(taskBean, holder));
        BreakpointInfo info = downloadTask.getInfo();
        Utils.calcProgressToView(holder.progressBar, info.getTotalOffset(), info.getTotalLength());
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
            holder.statusView.setText("报错");
            holder.downloadBtn.setText("重新开始");
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public TaskBean getItem(int position) {
        return beanList.get(position);
    }

    private class MyHtDownloadListener implements HtDownloadListener.EndCallback {

        private TaskBean taskBean;
        private Holder holder;

        public MyHtDownloadListener(TaskBean taskBean, Holder holder) {
            this.taskBean = taskBean;
            this.holder = holder;
        }

        @Override
        public void onEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
            if (cause == EndCause.COMPLETED) {
                taskBean.setStatus(3);
                holder.statusView.setText("完成");
                holder.downloadBtn.setText("打开");
            } else {
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
                    taskBean.getDownloadTask().enqueue(taskBean.getHtDownloadListener());
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
                    taskBean.getDownloadTask().enqueue(taskBean.getHtDownloadListener());
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
}
