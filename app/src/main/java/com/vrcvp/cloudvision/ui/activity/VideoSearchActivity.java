package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.vrcvp.cloudvision.BuildConfig;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.VideoBean;
import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.presenter.VideoSearchPresenter;
import com.vrcvp.cloudvision.ui.adapter.VideoAdapter;
import com.vrcvp.cloudvision.ui.widget.TipPageView;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.IVideoSearchView;

import java.util.List;

/**
 * 云展视频搜索Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoSearchActivity extends BaseActivity implements IVideoSearchView {

    private VideoSearchPresenter mVideoSearchPresenter;

    private EditText mEtKeyword;
    private PullListView mLvVideo;
    private TipPageView mTipPageView;

    private VideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_search);

        mVideoSearchPresenter = new VideoSearchPresenter(this, this);

        initView();
    }

    @Override
    protected void onDestroy() {
        mVideoSearchPresenter.onDestroy();
        cancelLoadingDialog();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_video_search_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_video_search:
                search();
                break;
            case R.id.tpv_video_search:
                search();
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
        cancelLoadingDialog();
        showShortToast(R.string.str_input_keyword);
    }

    @Override
    public void onSearchVideoResult(QueryVideoResp result) {
        mLvVideo.refreshCompleted();
        mLvVideo.loadMoreCompleted(mVideoSearchPresenter.hasMore());
        if(null == result) {
            // 错误
            if(mVideoSearchPresenter.isLoadMore()) {
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
                        if(mVideoSearchPresenter.isLoadMore()) {
                            showShortToast(R.string.str_no_more_data);
                        } else {
                            mTipPageView.setTips(R.drawable.ic_no_data, R.string.str_no_data,
                                    R.color.colorTextOrange);
                            mTipPageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mAdapter.addAll(video, true);
                        mTipPageView.setVisibility(View.GONE);
                    }
                    break;
                case HttpStatus.SC_NO_MORE_DATA:
                    showShortToast(R.string.str_no_more_data);
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // 无网络，读取缓存错误或者没有缓存
                default:
                    // 错误
                    if(mVideoSearchPresenter.isLoadMore()) {
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
        mEtKeyword = (EditText) findViewById(R.id.et_video_search_keyword);
        mTipPageView = (TipPageView) findViewById(R.id.tpv_video_search);

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
                final VideoBean video = mAdapter.getItem(position);
                if(null == video) {
                    return;
                }

                final String videoUrl = video.getVideoUrl();
                if(StringUtils.isEmpty(videoUrl)) {
                    return;
                }
                Intent i = new Intent(VideoSearchActivity.this, VideoPlayerActivity.class);
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
                mAdapter.clear(true);
                mVideoSearchPresenter.search();
            }
        });
        mLvVideo.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 加载下一页
                mVideoSearchPresenter.nextPage();
            }
        });

        mEtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(EditorInfo.IME_ACTION_SEARCH == actionId) {
                    hideSoftInputFromWindow(mEtKeyword);
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 搜索
     */
    private void search() {
        hideSoftInputFromWindow(mEtKeyword);
        showLoadingDialog(null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mVideoSearchPresenter.cancelLoadDataTask();
            }
        });
        mAdapter.clear(true);
        mVideoSearchPresenter.search();
    }
}
