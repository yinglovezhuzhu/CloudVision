package com.xunda.cloudvision.presenter;

import android.content.Context;

import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
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

    public VideoPresenter(Context context, IVideoView view) {
        this.mView = view;
        this.mModel = new VideoModel(context);
    }

    /**
     * 查询视频
     */
    public void queryVideo() {
        mModel.queryVideo(new HttpAsyncTask.Callback<QueryVideoResp>() {
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
                mView.onQueryVideoResult(result);
            }
        });
    }
}
