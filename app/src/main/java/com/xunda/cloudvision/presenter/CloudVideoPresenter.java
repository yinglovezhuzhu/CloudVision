package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.CloudVideoModel;
import com.xunda.cloudvision.model.ICloudVideoModel;
import com.xunda.cloudvision.view.ICloudVideoView;

/**
 * 云展视频Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class CloudVideoPresenter {

    private ICloudVideoView mView;
    private ICloudVideoModel mModel;

    public  CloudVideoPresenter(ICloudVideoView view) {
        this.mView = view;
        this.mModel = new CloudVideoModel();
    }
}
