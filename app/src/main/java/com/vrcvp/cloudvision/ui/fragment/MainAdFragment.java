package com.vrcvp.cloudvision.ui.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AdvertiseBean;
import com.vrcvp.cloudvision.listener.VideoPlayListener;
import com.vrcvp.cloudvision.presenter.VideoPlayerPresenter;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.IVideoPlayerView;

/**
 * 首页公告Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/10/25.
 */

public class MainAdFragment extends BaseFragment implements IVideoPlayerView {

    private View mContentView;
    private ImageView mIvImage;
    private ImageView mIvPlay;
    private AdvertiseBean mData;
    private VideoView mVideoView;
    private View mProgressView;
    private VideoPlayerPresenter mVideoPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_main_ad, container, false);

        initView(mContentView);

        mVideoPlayer = new VideoPlayerPresenter(getActivity(), this, null, new VideoPlayListener() {
            @Override
            public void onCompletion() {
                if(null == mData || AdvertiseBean.TYPE_VIDEO != mData.getType()) {
                    return;
                }
                if(null != mVideoPlayer) {
                    mVideoPlayer.play();
                }
            }

            @Override
            public void onError(int what, String msg) {
                if(null == mData || AdvertiseBean.TYPE_VIDEO != mData.getType())  {
                    return;
                }
                switch (what) {
                    case VideoPlayListener.WHAT_DOWNLOAD_ERROR:
                        hideLoadingProgress();
                        mVideoView.setVisibility(View.GONE);
                        mIvImage.setVisibility(View.VISIBLE);
                        mIvPlay.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
        mVideoPlayer.onCreate();

        return mContentView;
    }

    @Override
    public void onDestroyView() {
        if(null != mVideoPlayer) {
            mVideoPlayer.onDestroy();
        }
        if(null != mVideoView) {
            mVideoView.stopPlayback();
        }
        super.onDestroyView();
        mIvImage = null;
        mIvPlay = null;
        mVideoView = null;
        mProgressView = null;
    }

    @Override
    public void onPause() {
        if(null != mVideoPlayer) {
            mVideoPlayer.onPause();
        }
        if(null != mVideoView) {
//            mVideoView.suspend();
            mVideoView.stopPlayback();
            mVideoView.setVideoURI(null);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if(null != mVideoPlayer) {
            mVideoPlayer.onResume();
        }
        if(null != mVideoView && null != mData && AdvertiseBean.TYPE_VIDEO == mData.getType()) {
            setData(mData);
//            mVideoView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoPlayer.onDestroy();
        if(null != mVideoView) {
            mVideoView.stopPlayback();
        }
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
    public void playVideo(Uri uri, int position) {
        if(null == mVideoView || null == uri) {
            return;
        }
        mVideoView.setVideoURI(uri);
        mVideoView.seekTo(position);
        mVideoView.start();
        if(isPlaying()) {
            hideLoadingProgress();
        }
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

    /**
     * 设置数据，在Fragment视图初始化成功后调用
     * @param advertise 广告数据
     */
    public void setData(AdvertiseBean advertise) {
        if(null == advertise) {
            return;
        }
        mData = advertise;

        switch (advertise.getType()) {
            case AdvertiseBean.TYPE_IMAGE:
            case AdvertiseBean.TYPE_PRODUCT:
            case AdvertiseBean.TYPE_CORPORATE:
            case AdvertiseBean.TYPE_OUTER_LINK:
                mIvPlay.setVisibility(View.GONE);
                mVideoView.setVisibility(View.GONE);
                mProgressView.setVisibility(View.GONE);
                loadImage(advertise.getUrl(), mIvImage);
                break;
            case AdvertiseBean.TYPE_VIDEO:
                mVideoView.setVisibility(View.VISIBLE);
                final String url = mData.getUrl();
                if(null != mVideoPlayer && !StringUtils.isEmpty(url)) {
//                        Uri uri = Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4");
                    final Uri uri = Uri.parse(url);
                    mVideoPlayer.setVideoUri(uri);
                    mVideoPlayer.play();
                } else {
                    mIvPlay.setVisibility(View.VISIBLE);
                }
                loadImage(advertise.getOutLink(), mIvImage);
                break;
            default:
                break;
        }
    }

    /**
     * 清除数据
     */
    public void clearData() {
        mData = null;
        mIvImage.setImageBitmap(null);
        mIvPlay.setVisibility(View.GONE);
    }

    /**
     * 重新调整VideoView大小
     */
    public void resizeVideoView() {
        if(null != mVideoView) {
            SurfaceHolder holder = mVideoView.getHolder();
            if(null != holder) {
                holder.setSizeFromLayout();
            }
        }
    }

    private void initView(View contentView) {
        mIvImage = (ImageView) contentView.findViewById(R.id.iv_fragment_main_ad_img);
        mIvPlay = (ImageView) contentView.findViewById(R.id.iv_fragment_main_ad_video_play);
        mVideoView = (VideoView) contentView.findViewById(R.id.vv_fragment_main_ad_video);
        mProgressView = contentView.findViewById(R.id.ll_fragment_main_add_progress);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == mData) {
                    return;
                }
                switch (mData.getType()) {
                    case AdvertiseBean.TYPE_VIDEO:
                        if(null != mVideoPlayer && null != mVideoView && !mVideoView.isPlaying()) {
                            mVideoView.setVisibility(View.VISIBLE);
                            mIvImage.setVisibility(View.GONE);
                            mIvPlay.setVisibility(View.GONE);
                            mVideoPlayer.play();
                        }
                        break;
                    default:
                        openWebView(mData.getOutLink());
                        break;
                }
            }
        });
        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == mData) {
                    return;
                }
                switch (mData.getType()) {
                    case AdvertiseBean.TYPE_VIDEO:
//                        playVideo(mData.getOutLink());
                        break;
                    case AdvertiseBean.TYPE_PRODUCT:
                        openWebView(mData.getOutLink());
                        break;
                    case AdvertiseBean.TYPE_CORPORATE:
                        openWebView(mData.getOutLink());
                        break;
                    case AdvertiseBean.TYPE_OUTER_LINK:
                        openWebView(mData.getOutLink());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
