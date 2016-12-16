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

package com.vrcvp.cloudvision.presenter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.vrcvp.cloudvision.bean.DownloadLog;
import com.vrcvp.cloudvision.db.DownloadDBUtils;
import com.vrcvp.cloudvision.downloader.DownloadListener;
import com.vrcvp.cloudvision.listener.VideoPlayListener;
import com.vrcvp.cloudvision.model.IVideoPlayerModel;
import com.vrcvp.cloudvision.model.VideoPlayerModel;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.view.IVideoPlayerView;

import java.io.File;


public class VideoPlayerPresenter implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private static final int CACHE_MIN_SIZE = 1024 * 1024;

    private Context mContext;
    private IVideoPlayerView mView;
    private IVideoPlayerModel mModel;

    private Uri mVideoCacheUri; // 视频缓存地址（播放文件地址）
    private Uri mVideoUri; // 视频地址，如果是本地视频，跟缓存地址一样，在线视频为在线地址
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

    private boolean mDestroyed = true;

    private final Runnable mPlayingChecker = new Runnable() {
        public void run() {
            if (mView.isPlaying()) {
                mView.hideLoadingProgress();
            } else {
                mHandler.postDelayed(mPlayingChecker, 250);
            }
        }
    };

    public VideoPlayerPresenter(Context context, IVideoPlayerView view, VideoPlayListener listener) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new VideoPlayerModel(context, new DownloadListener() {
            @Override
            public void onProgressUpdate(int downloadedSize, int totalSize) {
//                Log.e("VideoPlayerPresenter", downloadedSize + " / " + totalSize);
                if(mOnError) {
                    mCurrentPosition = mView.getCurrentPosition();
                    if(!mCaching) {
                        mStartCachingSize = downloadedSize;
                        mCaching = true;
                    }
                    if(downloadedSize - mStartCachingSize > totalSize / 20) {
                        if(null == mVideoCacheUri) {
                            mVideoCacheUri = Uri.fromFile(mModel.getSavedVideoFile());
                        }
                        mCaching = false;
                        mOnError = false;
                        if(!mDestroyed) {
                            mView.playVideo(mVideoCacheUri, mCurrentPosition);
                            mView.hideLoadingProgress();
                        }
                    }
                } else {
                    if(null == mVideoCacheUri) {
                        mVideoCacheUri = Uri.fromFile(mModel.getSavedVideoFile());
                        if(!mDestroyed) {
                            mView.playVideo(mVideoCacheUri, 0);
                        }
                    } else {
                        playVideo(mVideoCacheUri);
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

        mView.setOnPreparedListener(this);
        mView.setOnErrorListener(this);
        mView.setOnCompletionListener(this);


    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mOnError = true;
        mHandler.removeCallbacksAndMessages(null);
        mView.showLoadingProgress();
        mCurrentPosition = mp.getCurrentPosition();
        if(mModel.isDownloadStopped()) {
            mModel.downloadVideo(mVideoUri.toString());
        }
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
        LogUtils.d("VideoPlayerPresenter", "MediaPlayer onPrepared------------------");
//        if(!mDestroyed && !mView.isPlaying() && null != mVideoCacheUri) {
//            mView.playVideo(mVideoCacheUri, 0);
//        }
    }

    /**
     * 播放视频
     * @param videoUri 视频地址
     */
    public void playVideo(Uri videoUri) {
        if(null == videoUri) {
            return;
        }
        // For streams that we expect to be slow to start up, show a
        // progress spinner until playback starts.
        mVideoUri = videoUri;
//        mView.playVideo(mVideoUri, 0);
        String scheme = mVideoUri.getScheme();
        if (null != scheme && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")
                || scheme.equalsIgnoreCase("ftp") || "rtsp".equalsIgnoreCase(scheme))) {
            // 网络视频
            final String url = mVideoUri.toString();
            File cacheFile;
            DownloadLog history = DownloadDBUtils.getHistoryByUrl(mContext, url);
            if(null != history && (cacheFile = new File(history.getSavedFile())).exists()) {
                // 网络视频，且已经有下载记录,并且缓存存在，直接播放缓存
                mView.hideLoadingProgress();
                mVideoCacheUri = Uri.fromFile(cacheFile);

                if(!mDestroyed) {
                    mView.playVideo(mVideoCacheUri, 0);
                }
            } else {
                // 网络视频，没有下载记录（未下载完成或者还没有开始下载）
                mHandler.postDelayed(mPlayingChecker, 250);
                DownloadLog log = DownloadDBUtils.getLogByUrl(mContext, url);
                if(null != log) {
                    cacheFile = new File(log.getSavedFile());
                    if(cacheFile.exists()) {
                        mVideoCacheUri = Uri.fromFile(cacheFile);

                        if(!mDestroyed) {
                            mView.playVideo(mVideoCacheUri, 0);
                        }
                    } else {
                        // 缓存文件丢失，删除下载日志
                        DownloadDBUtils.deleteLog(mContext, url);
                    }
                }

                mModel.downloadVideo(url);
            }

        } else {
            mVideoCacheUri = videoUri;
            mView.hideLoadingProgress();
            if(!mDestroyed) {
                mView.playVideo(mVideoCacheUri, 0);
            }
        }
    }

    /**
     * 重新播放
     */
    public void replayVideo() {
        playVideo(mVideoUri);
    }

    public void onCreate() {
        mDestroyed = false;
        mModel.onCreate();
    }

    public void onPause() {
        mHandler.removeCallbacksAndMessages(null);
        mModel.onPause();
    }

    public void onResume() {
        mModel.onResume();
    }

    public void onDestroy() {
        if(mDestroyed) {
            return;
        }
        mDestroyed = true;
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
