package com.github.xiaohu409.androidutildemo;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class HtDownloadListener extends DownloadListener4WithSpeed {

    private ProgressBar progressBar;
    private long totalLength;
    private EndCallback endCallback;

    public HtDownloadListener(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void taskStart(@NonNull DownloadTask task) {
    }

    @Override
    public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

    }

    @Override
    public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

    }

    @Override
    public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
        totalLength = info.getTotalLength();
        progressBar.setMax((int) totalLength);
    }

    @Override
    public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
        Utils.calcProgressToView(progressBar, currentOffset, totalLength);
    }

    @Override
    public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
        if (endCallback != null) {
            endCallback.onEnd(task, cause, realCause);
        }
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public interface EndCallback {
        void onEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause);
    }

    public void setEndCallback(EndCallback endCallback) {
        this.endCallback = endCallback;
    }
}
