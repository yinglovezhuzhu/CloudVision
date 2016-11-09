package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.VideoBean;
import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.presenter.VideoPresenter;
import com.vrcvp.cloudvision.ui.adapter.VideoAdapter;
import com.vrcvp.cloudvision.ui.widget.TipPageView;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.IVideoView;

import java.util.List;

/**
 * 云展视频Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoActivity extends BaseActivity implements IVideoView {

    private VideoPresenter mVideoPresenter;

    private TipPageView mTipPageView;
    private PullListView mLvVideo;
    private VideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        mVideoPresenter = new VideoPresenter(this, this);

        initView();

        queryVideo();
    }

    @Override
    protected void onDestroy() {
        mVideoPresenter.cancelQueryVideo();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_video_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.ibtn_video_search:
                startActivity(new Intent(this, VideoSearchActivity.class));
                break;
            case R.id.tpv_video_list:
                queryVideo();
                break;
            default:
                break;
        }
    }

    @Override
    public void onQueryVideoResult(QueryVideoResp result) {
        mLvVideo.refreshCompleted();
        mLvVideo.loadMoreCompleted(mVideoPresenter.hasMore());
        if(null == result) {
            // 错误
            if(mVideoPresenter.isLoadMore()) {
                showShortToast(R.string.str_no_more_data);
            } else {
                mTipPageView.setTips(R.drawable.ic_network_error, R.string.str_network_error,
                        R.color.colorTextLightRed, R.string.str_touch_to_refresh, this);
                mTipPageView.setVisibility(View.VISIBLE);
            }
        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<VideoBean> video = result.getData();
                    if(null == video || video.isEmpty()) {
                        // 请求成功，但是没有数据
                        if(mVideoPresenter.isLoadMore()) {
                            showShortToast(R.string.str_no_more_data);
                        } else {
                            mTipPageView.setTips(R.drawable.ic_no_data, R.string.str_no_data,
                                    R.color.colorTextOrange, R.string.str_touch_to_refresh, this);
                            mTipPageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mAdapter.addAll(video, true);
                    }
                    break;
                case HttpStatus.SC_NO_MORE_DATA:
                    showShortToast(R.string.str_no_more_data);
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // 无网络，读取缓存错误或者没有缓存
                default:
                    // 错误
                    if(mVideoPresenter.isLoadMore()) {
                        showShortToast(R.string.str_network_error);
                    } else {
                        mTipPageView.setTips(R.drawable.ic_network_error, R.string.str_network_error,
                                R.color.colorTextLightRed, R.string.str_touch_to_refresh, this);
                        mTipPageView.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
        cancelLoadingDialog();
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {
        cancelLoadingDialog();
    }

    private void initView() {
        mTipPageView = (TipPageView) findViewById(R.id.tpv_video_list);
        findViewById(R.id.ibtn_video_back).setOnClickListener(this);
        findViewById(R.id.ibtn_video_search).setOnClickListener(this);

        mLvVideo = (PullListView) findViewById(R.id.lv_video);
        mAdapter = new VideoAdapter(this, getResources().getDisplayMetrics().widthPixels);
        mLvVideo.setAdapter(mAdapter);
        mLvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final VideoBean video = mAdapter.getItem(position);
                if(null == video) {
                    return;
                }
                final String videoUrl = video.getVideoUrl();
                if(StringUtils.isEmpty(videoUrl)) {
                    return;
                }
                Intent i = new Intent(VideoActivity.this, VideoPlayerActivity.class);
                i.setData(Uri.parse(videoUrl));
                i.putExtra(Config.EXTRA_TITLE_STR, video.getName());
                startActivity(i);
            }
        });
        mLvVideo.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvVideo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                mVideoPresenter.queryVideoFirstPage();
            }
        });
        mLvVideo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 加载下一页
                mVideoPresenter.queryVideoNextPage();
            }
        });
    }

    private void queryVideo() {
//        mTipPageView.setVisibility(View.GONE);
        showLoadingDialog(null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mVideoPresenter.cancelQueryVideo();
            }
        });
        mVideoPresenter.queryVideoFirstPage();
    }
}
