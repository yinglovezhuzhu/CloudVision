package com.xunda.cloudvision.presenter;

import android.content.Context;

import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
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

    public VideoSearchPresenter(Context context, IVideoSearchView view) {
        this.mView = view;
        this.mModel = new VideoSearchModel(context);
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

        mModel.searchVideo(keyword, new HttpAsyncTask.Callback<QueryVideoResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mView.onCanceled(null);
            }

            @Override
            public void onResult(QueryVideoResp result) {
                mView.onSearchVideoResult(result);
            }
        });
    }
}
