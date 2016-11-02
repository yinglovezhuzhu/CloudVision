package com.vrcvp.cloudvision.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.AdvertiseBean;
import com.vrcvp.cloudvision.ui.activity.VideoActivity;
import com.vrcvp.cloudvision.ui.activity.VideoPlayerActivity;
import com.vrcvp.cloudvision.ui.activity.WebViewActivity;

/**
 * 首页公告Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/10/25.
 */

public class MainAdFragment extends BaseFragment {

    private ImageView mIvImage;
    private ImageView mIvPlay;
    private AdvertiseBean mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_main_ad, container, false);

        initView(contentView);

        return contentView;
    }

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
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
                i.putExtra(Config.EXTRA_TITLE_STR, "年度最佳电影剪辑");
                startActivity(i);

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
