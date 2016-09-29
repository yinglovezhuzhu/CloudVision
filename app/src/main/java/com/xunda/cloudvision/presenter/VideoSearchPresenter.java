package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.CloudVideoModel;
import com.xunda.cloudvision.model.ICloudVideoModel;
import com.xunda.cloudvision.model.IVideoSearchModel;
import com.xunda.cloudvision.model.VideoSearchModel;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IVideoSearchView;

/**
 * 云展视频搜索Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoSearchPresenter {

    private IVideoSearchView mView;
    private IVideoSearchModel mModel;

    public VideoSearchPresenter(IVideoSearchView view) {
        this.mView = view;
        this.mModel = new VideoSearchModel();
    }

    /**
     * 搜索
     */
    public void search() {
        String keyword = mView.getKeyword();
        if(StringUtils.isEmpty(keyword)) {
            mView.onKeywordEmptyError();
            return;
        }
    }
}
