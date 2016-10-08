/*
 * Copyright (C) 2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.xunda.cloudvision.bean;

/**
 * 下载日志数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/3.
 */

public class DownloadLog {
    private long id;
    private String url;
    private int downloadedSize;
    private int totalSize;
    private String savedFile;
    private boolean endDownloaded = false; // 文件尾部是否已经下载， false 未下载， true 已下载
    private long finishedTime = System.currentTimeMillis();
    private boolean locked = false;

    public DownloadLog() {

    }

    public DownloadLog(String url, int downloadedSize,
                       int totalSize, String savedFile) {
        this.url = url;
        this.downloadedSize = downloadedSize;
        this.totalSize = totalSize;
        this.savedFile = savedFile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(int downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(String savedFile) {
        this.savedFile = savedFile;
    }

    public boolean isEndDownloaded() {
        return endDownloaded;
    }

    public void setEndDownloaded(boolean endDownloaded) {
        this.endDownloaded = endDownloaded;
    }

    public long getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(long finishedTime) {
        this.finishedTime = finishedTime;
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return "DownloadLog{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", downloadedSize=" + downloadedSize +
                ", totalSize=" + totalSize +
                ", savedFile='" + savedFile + '\'' +
                ", endDownloaded=" + endDownloaded +
                ", finishedTime=" + finishedTime +
                ", locked=" + locked +
                '}';
    }
}
