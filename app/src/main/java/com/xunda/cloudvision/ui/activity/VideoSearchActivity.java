package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.VideoBean;
import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.presenter.VideoSearchPresenter;
import com.xunda.cloudvision.ui.adapter.VideoAdapter;
import com.xunda.cloudvision.view.IVideoSearchView;

import java.util.List;

/**
 * 云展视频搜索Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoSearchActivity extends BaseActivity implements IVideoSearchView {

    private VideoSearchPresenter mVideoSearchPresenter;

    private EditText mEtKeyword;
    private PullListView mLvVideo;

    private VideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_search);

        mVideoSearchPresenter = new VideoSearchPresenter(this, this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_video_search_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_video_search:
                mVideoSearchPresenter.search();
                hideSoftInputFromWindow(mEtKeyword);
                break;
            default:
                break;
        }
    }

    @Override
    public String getKeyword() {
        if(null == mEtKeyword) {
            return null;
        }
        return mEtKeyword.getText().toString().trim();
    }

    @Override
    public void onKeywordEmptyError() {
        showShortToast(R.string.str_input_keyword);
    }

    private void initView() {
        mEtKeyword = (EditText) findViewById(R.id.et_video_search_keyword);

        findViewById(R.id.ibtn_video_search_back).setOnClickListener(this);
        findViewById(R.id.btn_video_search).setOnClickListener(this);

        mLvVideo = (PullListView) findViewById(R.id.lv_video_search);
        final int width = getResources().getDisplayMetrics().widthPixels
                - getResources().getDimensionPixelSize(R.dimen.contentPadding_level4) * 2;
        mAdapter = new VideoAdapter(this, width);
        mLvVideo.setAdapter(mAdapter);
        mLvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(VideoSearchActivity.this, VideoPlayerActivity.class);
                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
                startActivity(i);
            }
        });
        final Handler handler = new Handler();
        mLvVideo.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvVideo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLvVideo.refreshCompleted();
                    }
                }, 3000);
            }
        });
        mLvVideo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // TODO 加载下一页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLvVideo.loadMoreCompleted(true);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onSearchVideoResult(QueryVideoResp result) {
        mLvVideo.refreshCompleted();
        mLvVideo.loadMoreCompleted(true);
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<VideoBean> video = result.getVideo();
                    if(null == video || video.isEmpty()) {
                        // TODO 错误
                    } else {
                        mAdapter.addAll(video, true);
                    }
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // TODO 无网络，读取缓存错误
                    break;
                default:
                    // TODO 错误
                    break;
            }
        }
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }
}
