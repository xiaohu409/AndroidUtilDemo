package com.github.xiaohu409.androidutildemo.filedownload;

import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;

public class TaskBean {

    private String fileName;
    private String fileNameUrl;
    //0.未下载 1.开始下载 2.暂停下载 3.下载完成 4.报错 5.取消 6.其他异常
    private int status;
    private DownloadTask downloadTask;

    private DownloadListener downloadListener;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameUrl() {
        return fileNameUrl;
    }

    public void setFileNameUrl(String fileNameUrl) {
        this.fileNameUrl = fileNameUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DownloadTask getDownloadTask() {
        return downloadTask;
    }

    public void setDownloadTask(DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    public DownloadListener getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }
}
