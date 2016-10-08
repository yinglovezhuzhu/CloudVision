package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.CloudVideoPresenter;
import com.xunda.cloudvision.presenter.VideoSearchPresenter;
import com.xunda.cloudvision.ui.adapter.CloudVideoAdapter;
import com.xunda.cloudvision.view.IVideoSearchView;

/**
 * 云展视频搜索Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoSearchActivity extends BaseActivity implements IVideoSearchView {

    private VideoSearchPresenter mVideoSeaarchPresenter;

    private EditText mEtKeyword;

    private CloudVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_search);

        mVideoSeaarchPresenter = new VideoSearchPresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_video_search_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.btn_video_search:
                mVideoSeaarchPresenter.search();
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

        final ListView lvVideo = (ListView) findViewById(R.id.lv_video_search);
        final int width = getResources().getDisplayMetrics().widthPixels
                - getResources().getDimensionPixelSize(R.dimen.contentPadding_level4) * 2;
        mAdapter = new CloudVideoAdapter(this, width);
        lvVideo.setAdapter(mAdapter);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(VideoSearchActivity.this, VideoPlayerActivity.class);
                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
                startActivity(i);
            }
        });
    }
}
