package com.vrcvp.cloudvision.ui.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AdvertiseBean;
import com.vrcvp.cloudvision.listener.VideoPlayListener;
import com.vrcvp.cloudvision.presenter.VideoPlayerPresenter;
import com.vrcvp.cloudvision.ui.activity.VideoActivity;
import com.vrcvp.cloudvision.ui.activity.VideoPlayerActivity;
import com.vrcvp.cloudvision.ui.activity.WebViewActivity;
import com.vrcvp.cloudvision.view.IVideoPlayerView;

/**
 * 首页公告Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/10/25.
 */

//public class MainAdFragment extends BaseFragment implements IVideoPlayerView {
public class MainAdFragment extends BaseFragment {

    private ImageView mIvImage;
    private ImageView mIvPlay;
    private AdvertiseBean mData;
//    private VideoView mVideoView;
//    private View mProgress;
//    private VideoPlayerPresenter mVideoPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_main_ad, container, false);

        initView(contentView);

        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIvImage = null;
        mIvPlay = null;
//        mVideoView = null;
//        mProgress = null;
    }

//    @Override
//    public void onPause() {
//        if(null != mVideoPlayer) {
//            mVideoPlayer.onPause();
//        }
//        if(null != mVideoView) {
//            mVideoView.suspend();
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        if(null != mVideoPlayer) {
//            mVideoPlayer.onResume();
//        }
//        if(null != mVideoView) {
//            mVideoView.resume();
//        }
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        if(null != mVideoPlayer) {
//            mVideoPlayer.onDestroy();
//        }
//        if(null != mVideoView) {
//            mVideoView.stopPlayback();
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
//        if(null != mVideoView) {
//            mVideoView.setOnErrorListener(listener);
//        }
//    }
//
//    @Override
//    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
//        if(null != mVideoView) {
//            mVideoView.setOnCompletionListener(listener);
//        }
//    }
//
//    @Override
//    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
//        if(null != mVideoView) {
//            mVideoView.setOnPreparedListener(listener);
//        }
//    }
//
//    @Override
//    public void playVideo(Uri uri, int position) {
//        if(null == mVideoView || null == uri) {
//            return;
//        }
//        mVideoView.setVideoURI(uri);
//        mVideoView.seekTo(position);
//        mVideoView.start();
//    }
//
//    @Override
//    public boolean isPlaying() {
//        if(null == mVideoView) {
//            return false;
//        }
//        return mVideoView.isPlaying();
//    }
//
//    @Override
//    public void showLoadingProgress() {
//        if(null == mProgress) {
//            return;
//        }
//        mProgress.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideLoadingProgress() {
//        if(null == mProgress) {
//            return;
//        }
//        mProgress.setVisibility(View.GONE);
//    }

    /**
     * 设置数据
     * @param advertise
     */
    public void setData(AdvertiseBean advertise) {
        if(null == advertise) {
            return;
        }
        mData = advertise;
        if(null != mIvImage) {
            loadImage(advertise.getUrl(), mIvImage);
        }

        if(null != mIvPlay) {
            switch (advertise.getType()) {
                case AdvertiseBean.TYPE_IMAGE:
                    mIvPlay.setVisibility(View.GONE);
                    break;
                case AdvertiseBean.TYPE_VIDEO:
                    mIvPlay.setVisibility(View.VISIBLE);
//                    Uri uri = Uri.parse(path);
//                    mVideoPlayer = new VideoPlayerPresenter(getActivity(), this, uri, new VideoPlayListener() {
//                        @Override
//                        public void onCompletion() {
//
//                        }
//
//                        @Override
//                        public void onError(int what, String msg) {
//
//                        }
//                    });
//                    mVideoPlayer.onCreate();
                    break;
                case AdvertiseBean.TYPE_PRODUCT:
                    mIvPlay.setVisibility(View.GONE);
                    break;
                case AdvertiseBean.TYPE_CORPORATE:
                    mIvPlay.setVisibility(View.GONE);
                    break;
                case AdvertiseBean.TYPE_OUTER_LINK:
                    mIvPlay.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
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

    private void initView(View contentView) {
        mIvImage = (ImageView) contentView.findViewById(R.id.iv_fragment_main_ad_img);
        mIvPlay = (ImageView) contentView.findViewById(R.id.iv_fragment_main_ad_video_play);
//        mVideoView = (VideoView) contentView.findViewById(R.id.vv_fragment_main_ad_video);
//        mProgress = contentView.findViewById(R.id.ll_fragment_main_add_progress);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
//                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
//                i.putExtra(Config.EXTRA_TITLE_STR, "年度最佳电影剪辑");
//                startActivity(i);

//                if(null == mData) {
//                    return;
//                }
//                switch (mData.getType()) {
//                    case AdvertiseBean.TYPE_IMAGE:
//                        openWebView(mData.getOutLink());
//                        break;
//                    case AdvertiseBean.TYPE_VIDEO:
//                        playVideo(mData.getOutLink());
//                        break;
//                    case AdvertiseBean.TYPE_PRODUCT:
//                        openWebView(mData.getOutLink());
//                        break;
//                    case AdvertiseBean.TYPE_CORPORATE:
//                        openWebView(mData.getOutLink());
//                        break;
//                    case AdvertiseBean.TYPE_OUTER_LINK:
//                        openWebView(mData.getOutLink());
//                        break;
//                    default:
//                        break;
//                }
            }
        });
    }



    /**
     * 播放视频
     * @param path
     */
    private void playVideo(String path) {
        Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
        i.setData(Uri.parse(path));
        startActivity(i);
    }

    /**
     * 打开网页
     * @param url
     */
    private void openWebView(String url) {
        Intent i = new Intent(getActivity(), WebViewActivity.class);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
