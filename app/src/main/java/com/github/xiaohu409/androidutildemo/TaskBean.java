package com.github.xiaohu409.androidutildemo;

import com.liulishuo.okdownload.DownloadTask;

public class TaskBean {

    private String fileName;
    private String fileNameUrl;
    //0.未下载 1.开始下载 2.暂停下载 3.下载完成 4.报错 5 取消
    private int status;
    private DownloadTask downloadTask;
    private HtDownloadListener htDownloadListener;

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

    public HtDownloadListener getHtDownloadListener() {
        return htDownloadListener;
    }

    public void setHtDownloadListener(HtDownloadListener htDownloadListener) {
        this.htDownloadListener = htDownloadListener;
    }
}
