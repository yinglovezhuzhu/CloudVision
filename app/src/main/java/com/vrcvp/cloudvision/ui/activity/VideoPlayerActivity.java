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

package com.vrcvp.cloudvision.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.listener.VideoPlayListener;
import com.vrcvp.cloudvision.presenter.VideoPlayerPresenter;
import com.vrcvp.cloudvision.view.IVideoPlayerView;

/**
 * This activity plays a video from a specified URI.
 */
public class VideoPlayerActivity extends BaseActivity implements IVideoPlayerView {

    private VideoView mVideoView;
    private ImageView mIvThumb;
    private View mProgressView;
    private ImageView mIvPlay;

    private VideoPlayerPresenter mVideoPlayer;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_video_player);

        mVideoView = (VideoView) findViewById(R.id.video_player_surface_view);
        mIvThumb = (ImageView) findViewById(R.id.iv_video_player_thumb);
        mProgressView = findViewById(R.id.video_player_progress_indicator);
        mIvPlay = (ImageView) findViewById(R.id.iv_video_player_play);
        findViewById(R.id.ibtn_video_player_back).setOnClickListener(this);
        findViewById(R.id.iv_video_player_play).setOnClickListener(this);
        final TextView tvTitle = (TextView) findViewById(R.id.tv_video_player_title);

//        mVideoView.setMediaController(new MediaController(this));
        // make the video view handle keys for seeking and pausing
        mVideoView.requestFocus();
        mVideoView.setKeepScreenOn(true);

        Intent intent = getIntent();

        if(intent.hasExtra(Config.EXTRA_THUMB_URL)) {
            loadImage(intent.getStringExtra(Config.EXTRA_THUMB_URL), mIvThumb, R.drawable.default_img, R.drawable.default_img);
        }

        mVideoPlayer = new VideoPlayerPresenter(this, this, intent.getData(), new VideoPlayListener() {
            @Override
            public void onCompletion() {
                finish();
            }

            @Override
            public void onError(int what, String msg) {
                switch (what) {
                    case VideoPlayListener.WHAT_DOWNLOAD_ERROR:
                        showShortToast(R.string.str_network_error);
                        break;
                    case VideoPlayListener.WHAT_URI_EMPTY:
                        showShortToast(R.string.str_video_play_failed);
                        break;
                    default:
                        break;
                }
                hideLoadingProgress();
                mIvPlay.setVisibility(View.VISIBLE);
                mIvThumb.setVisibility(View.VISIBLE);
            }
        });
        mVideoPlayer.onCreate();
        mIvThumb.setVisibility(View.GONE);
        mIvPlay.setVisibility(View.GONE);
        mVideoPlayer.play();

        if (intent.hasExtra(MediaStore.EXTRA_SCREEN_ORIENTATION)) {
            int orientation = intent.getIntExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            //noinspection ResourceType
            if (orientation != getRequestedOrientation()) {
                //noinspection ResourceType
                setRequestedOrientation(orientation);
            }
        }
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.buttonBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
        window.setAttributes(winParams);

        if(intent.hasExtra(Config.EXTRA_TITLE_STR)) {
            tvTitle.setText(intent.getStringExtra(Config.EXTRA_TITLE_STR));
        } else if(intent.hasExtra(Config.EXTRA_TITLE_RES)) {
            int resId = intent.getIntExtra(Config.EXTRA_TITLE_STR, 0);
            if(0 != resId) {
                tvTitle.setText(resId);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoPlayer.onPause();
        if(null != mVideoView) {
            mVideoView.suspend();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoPlayer.onResume();
        if(null != mVideoView) {
            mVideoView.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        if(null != mVideoView) {
            mVideoView.setOnErrorListener(listener);
        }
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        if(null != mVideoView) {
            mVideoView.setOnCompletionListener(listener);
        }
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
        if(null != mVideoView) {
            mVideoView.setOnPreparedListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_video_player_back:
                exit();
                break;
            case R.id.iv_video_player_play:
                mIvThumb.setVisibility(View.GONE);
                mIvPlay.setVisibility(View.GONE);
                mVideoPlayer.play();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public void playVideo(Uri uri, int position) {
        if(null == mVideoView || null == uri) {
            return;
        }
        mVideoView.setVideoURI(uri);
        mVideoView.seekTo(position);
        mVideoView.start();
    }

    @Override
    public boolean isPlaying() {
        if(null == mVideoView) {
            return false;
        }
        return mVideoView.isPlaying();
    }

    @Override
    public int getCurrentPosition() {
        if(null == mVideoView) {
            return 0;
        }
        return mVideoView.getCurrentPosition();
    }

    @Override
    public void showLoadingProgress() {
        if(null == mProgressView) {
            return;
        }
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        if(null == mProgressView) {
            return;
        }
        mProgressView.setVisibility(View.GONE);
    }

    private void exit() {
        mVideoPlayer.onDestroy();
        if(null != mVideoView) {
            mVideoView.stopPlayback();
        }
        finish(RESULT_CANCELED, null);
    }
}
