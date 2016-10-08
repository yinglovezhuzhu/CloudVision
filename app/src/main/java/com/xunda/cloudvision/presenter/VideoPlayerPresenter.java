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

package com.xunda.cloudvision.presenter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.DownloadLog;
import com.xunda.cloudvision.db.DownloadDBUtils;
import com.xunda.cloudvision.downloader.DownloadListener;
import com.xunda.cloudvision.listener.VideoPlayListener;
import com.xunda.cloudvision.model.IVideoPlayerModel;
import com.xunda.cloudvision.model.VideoPlayerModel;
import com.xunda.cloudvision.view.IVideoPlayerView;

import java.io.File;


public class VideoPlayerPresenter implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private static final int CACHE_MIN_SIZE = 1024 * 1024;

    private IVideoPlayerView mView;
    private IVideoPlayerModel mModel;

    private Uri mUri;
    /** 当前播放进度 **/
    private int mCurrentPosition = 0;
    /** 当前处于错误状态下 **/
    private boolean mOnError = false;
    /** 正在缓存数据 **/
    private boolean mCaching = false;
    /** 开始缓存的下载长度 **/
    private long mStartCachingSize = 0;

    private VideoPlayListener mPlayListener = null;

    private final Handler mHandler = new Handler();

    private final Runnable mPlayingChecker = new Runnable() {
        public void run() {
            if (mView.isPlaying()) {
                mView.hideLoadingProgress();
            } else {
                mHandler.postDelayed(mPlayingChecker, 250);
            }
        }
    };

    public VideoPlayerPresenter(final Context context, IVideoPlayerView view,
                                Uri videoUri, VideoPlayListener listener) {
        this.mView = view;
        this.mModel = new VideoPlayerModel(context, videoUri.toString(), new DownloadListener() {
            @Override
            public void onProgressUpdate(int downloadedSize, int totalSize) {
                Log.e("VideoPlayerPresenter", downloadedSize + " / " + totalSize);
                if(mOnError) {
                    if(!mCaching) {
                        mStartCachingSize = downloadedSize;
                        mCaching = true;
                    }
                    if(downloadedSize - mStartCachingSize > CACHE_MIN_SIZE) {
                        if(null == mUri) {
                            mUri = Uri.fromFile(mModel.getSavedVideoFile());
                        }
                        mCaching = false;
                        mOnError = false;
                        mView.playVideo(mUri, mCurrentPosition);
                        mView.hideLoadingProgress();
                    }
                } else {
                    if(null == mUri) {
                        mUri = Uri.fromFile(mModel.getSavedVideoFile());
                        mView.playVideo(mUri, 0);
                    }
                }
            }

            @Override
            public void onError(int code, String message) {
                if(null != mPlayListener) {
                    mPlayListener.onError(VideoPlayListener.WHAT_DOWNLOAD_ERROR,
                            "Video downloadVideo failed: " + message);
                }
            }
        });
        this.mPlayListener = listener;

        // For streams that we expect to be slow to start up, show a
        // progress spinner until playback starts.
        String scheme = videoUri.getScheme();
        if (null != scheme && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")
                || scheme.equalsIgnoreCase("ftp") || "rtsp".equalsIgnoreCase(scheme))) {
            // 网络视频
            final String url = videoUri.toString();
            File cacheFile;
            DownloadLog history = DownloadDBUtils.getHistoryByUrl(context, url);
            if(null != history && (cacheFile = new File(history.getSavedFile())).exists()) {
                // 网络视频，且已经有下载记录,并且缓存存在，直接播放缓存
                mView.hideLoadingProgress();
                mUri = Uri.fromFile(cacheFile);
                mView.playVideo(mUri, 0);
            } else {
                // 网络视频，没有下载记录（未下载完成或者还没有开始下载）
                mHandler.postDelayed(mPlayingChecker, 250);
                DownloadLog log = DownloadDBUtils.getLogByUrl(context, url);
                if(null != log) {
                    cacheFile = new File(log.getSavedFile());
                    if(cacheFile.exists()) {
                        mUri = Uri.fromFile(cacheFile);
                        mView.playVideo(mUri, 0);
                    } else {
                        // 缓存文件丢失，删除下载日志
                        DownloadDBUtils.deleteLog(context, url);
                    }
                }

                mModel.downloadVideo();
            }

        } else {
            mUri = videoUri;
            mView.hideLoadingProgress();
            mView.playVideo(mUri, 0);
        }
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mHandler.removeCallbacksAndMessages(null);
        mView.showLoadingProgress();
        mCurrentPosition = mp.getCurrentPosition();
        mOnError = true;
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(null != mPlayListener) {
            mPlayListener.onCompletion();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    public void onPause() {
        mHandler.removeCallbacksAndMessages(null);
        mModel.onPause();
    }

    public void onResume() {
        mModel.onResume();
    }

    public void onDestroy() {
        mModel.onDestroy();
    }

//    private String formatDuration(final Context context, int durationMs) {
//        int duration = durationMs / 1000;
//        int h = duration / 3600;
//        int m = (duration - h * 3600) / 60;
//        int s = duration - (h * 3600 + m * 60);
//        String durationValue;
//        if (h == 0) {
//            durationValue = String.format(context.getString(R.string.details_ms), m, s);
//        } else {
//            durationValue = String.format(context.getString(R.string.details_hms), h, m, s);
//        }
//        return durationValue;
//    }
}
