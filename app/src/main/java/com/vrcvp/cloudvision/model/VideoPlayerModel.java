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

package com.vrcvp.cloudvision.model;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Handler;

import com.vrcvp.cloudvision.bean.DownloadLog;
import com.vrcvp.cloudvision.db.DownloadDBUtils;
import com.vrcvp.cloudvision.downloader.DownloadListener;
import com.vrcvp.cloudvision.downloader.Downloader;
import com.vrcvp.cloudvision.observer.NetworkObserver;
import com.vrcvp.cloudvision.utils.NetworkManager;

import java.io.File;

/**
 * 视频播放器Model
 * Created by yinglovezhuzhu@gmail.com on 2016/10/8.
 */

public class VideoPlayerModel implements IVideoPlayerModel {

    private Context mContext;
    private Downloader mDownloader;
    private DownloadListener mDownloadListener;
    private String mUrl;
    private final Handler mHandler = new Handler();

    private NetworkObserver mNetworkObserver = new NetworkObserver() {
        @Override
        public void onNetworkStateChanged(boolean networkConnected, NetworkInfo currentNetwork, NetworkInfo lastNetwork) {
            if(networkConnected && null != mDownloader && mDownloader.isStop() && !mDownloader.isFinished()) {
                downloadVideo();
            }
        }
    };

    public VideoPlayerModel(Context context, String downloadUrl, DownloadListener listener) {
        this.mContext = context;
        this.mUrl = downloadUrl;
        mDownloader = new Downloader(context, mUrl,
                new File(context.getExternalCacheDir(), "Video"), null);
        this.mDownloadListener = listener;
    }

    @Override
    public void downloadVideo() {
        if(mDownloader.isStop()) {
            DownloadLog history = DownloadDBUtils.getHistoryByUrl(mContext, mUrl);
            if(null != history && (new File(history.getSavedFile())).exists()) {
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mDownloader.download(".mp4", new DownloadListener() {
                            @Override
                            public void onProgressUpdate(final int downloadedSize, final int totalSize) {
                                if(null != mDownloadListener) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDownloadListener.onProgressUpdate(downloadedSize, totalSize);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onError(final int code, final String message) {
                                if(null != mDownloadListener) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDownloadListener.onError(code, message);
                                        }
                                    });
                                }
                            }
                        });
                    } catch (final Exception e) {
                        if (null != mDownloadListener) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mDownloadListener.onError(DownloadListener.CODE_EXCEPTION, e.getMessage());
                                }
                            });
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public File getSavedVideoFile() {
        if(null == mDownloader) {
            return null;
        }
        return mDownloader.getSavedFile();
    }

    @Override
    public void onCreate() {
        // 初始化网络监听管理者
        NetworkManager.getInstance().initialized(mContext);
        NetworkManager.getInstance().registerNetworkObserver(mNetworkObserver);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        downloadVideo();
    }

    @Override
    public void onDestroy() {
        if(null != mDownloader) {
            mDownloader.stop();
        }
        NetworkManager.getInstance().unregisterNetworkObserver(mNetworkObserver);
    }

    @Override
    public boolean isDownloadStopped() {
        if(null == mDownloader) {
            return true;
        }
        return mDownloader.isStop();
    }
}
