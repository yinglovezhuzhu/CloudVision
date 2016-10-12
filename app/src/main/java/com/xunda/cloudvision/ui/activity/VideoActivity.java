package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.presenter.VideoPresenter;
import com.xunda.cloudvision.ui.adapter.CloudVideoAdapter;
import com.xunda.cloudvision.view.IVideoView;

/**
 * 云展视频Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoActivity extends BaseActivity implements IVideoView {

    private VideoPresenter mCloudVideoPresenter;

    private CloudVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cloud_video);

        mCloudVideoPresenter = new VideoPresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_cloud_video_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.ibtn_cloud_video_search:
                startActivity(new Intent(this, VideoSearchActivity.class));
                break;
            default:
                break;
        }
    }

    private void initView() {
        findViewById(R.id.ibtn_cloud_video_back).setOnClickListener(this);
        findViewById(R.id.ibtn_cloud_video_search).setOnClickListener(this);

        final PullListView lvVideo = (PullListView) findViewById(R.id.lv_cloud_video);
        mAdapter = new CloudVideoAdapter(this, getResources().getDisplayMetrics().widthPixels);
        lvVideo.setAdapter(mAdapter);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(VideoActivity.this, VideoPlayerActivity.class);
                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
                startActivity(i);
            }
        });
        final Handler handler = new Handler();
        lvVideo.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        lvVideo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvVideo.refreshCompleted();
                    }
                }, 3000);
            }
        });
        lvVideo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // TODO 加载下一页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvVideo.loadMoreCompleted(true);
                    }
                }, 3000);
            }
        });

    }

    @Override
    public void onQueryVideoResult(QueryVideoResp result) {

    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }
}
