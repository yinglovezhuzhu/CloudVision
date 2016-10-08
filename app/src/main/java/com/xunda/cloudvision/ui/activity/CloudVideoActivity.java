package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.CloudVideoPresenter;
import com.xunda.cloudvision.ui.adapter.CloudVideoAdapter;
import com.xunda.cloudvision.view.ICloudVideoView;

/**
 * 云展视频Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class CloudVideoActivity extends BaseActivity implements ICloudVideoView {

    private CloudVideoPresenter mCloudVideoPresenter;

    private CloudVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cloud_video);

        mCloudVideoPresenter = new CloudVideoPresenter(this);

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

        final ListView lvVideo = (ListView) findViewById(R.id.lv_cloud_video);
        mAdapter = new CloudVideoAdapter(this, getResources().getDisplayMetrics().widthPixels);
        lvVideo.setAdapter(mAdapter);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CloudVideoActivity.this, VideoPlayerActivity.class);
                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
                startActivity(i);
            }
        });
    }
}
