package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.VideoModel;
import com.xunda.cloudvision.model.IVideoModel;
import com.xunda.cloudvision.view.IVideoView;

/**
 * 云展视频Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoPresenter {

    private IVideoView mView;
    private IVideoModel mModel;

    public VideoPresenter(IVideoView view) {
        this.mView = view;
        this.mModel = new VideoModel();
    }
}
