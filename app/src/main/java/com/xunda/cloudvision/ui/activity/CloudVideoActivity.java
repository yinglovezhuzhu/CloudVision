package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.CloudVideoPresenter;
import com.xunda.cloudvision.view.ICloudVideoView;

/**
 * 云展视频Activity
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class CloudVideoActivity extends BaseActivity implements ICloudVideoView {

    private CloudVideoPresenter mCloudVideoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cloud_video);

        mCloudVideoPresenter = new CloudVideoPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_cloud_video_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.ibtn_cloud_video_search:
                break;
            default:
                break;
        }
    }

    private void initView() {

    }
}
