package com.vrcvp.cloudvision.ui.activity;

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
import com.vrcvp.cloudvision.view.IVideoView;

import java.util.List;

/**
 * 云展视频Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoActivity extends BaseActivity implements IVideoView {

    private VideoPresenter mVideoPresenter;

    private PullListView mLvVideo;
    private VideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        mVideoPresenter = new VideoPresenter(this, this);

        initView();

        mVideoPresenter.queryVideoFirstPage();
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
            default:
                break;
        }
    }

    private void initView() {
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
                Intent i = new Intent(VideoActivity.this, VideoPlayerActivity.class);
                i.setData(Uri.parse(video.getVideoUrl()));
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

    @Override
    public void onQueryVideoResult(QueryVideoResp result) {
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<VideoBean> video = result.getData();
                    if(null == video || video.isEmpty()) {
                        // TODO 错误
                    } else {
                        if(mLvVideo.isRefreshing()) {
                            mAdapter.clear(false);
                        }
                        mAdapter.addAll(video, true);
                    }
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // TODO 无网络，读取缓存错误
                    break;
                case HttpStatus.SC_NO_MORE_DATA:
                    showShortToast(R.string.str_no_more_data);
                    break;
                default:
                    // TODO 错误
                    break;
            }
        }
        mLvVideo.refreshCompleted();
        mLvVideo.loadMoreCompleted(mVideoPresenter.hasMoreVideo());
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }
}
