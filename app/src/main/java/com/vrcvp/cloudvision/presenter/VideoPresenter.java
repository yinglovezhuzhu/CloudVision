package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.model.VideoModel;
import com.vrcvp.cloudvision.model.IVideoModel;
import com.vrcvp.cloudvision.view.IVideoView;

/**
 * 云展视频Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class VideoPresenter {

    private IVideoView mView;
    private IVideoModel mModel;

    private int mVideoPage = 1;
    private boolean mHasMoreVideo = true;

    public VideoPresenter(Context context, IVideoView view) {
        this.mView = view;
        this.mModel = new VideoModel(context);
    }
    /**
     * 查询第一页数据
     */
    public void queryVideoFirstPage() {
        mVideoPage = 1;
        queryVideo(mVideoPage);
    }

    /**
     * 查询下一页数据
     */
    public void queryVideoNextPage() {
        if(mHasMoreVideo) {
            mVideoPage++;
            queryVideo(mVideoPage);
        } else {
            QueryVideoResp result = new QueryVideoResp();
            result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
            result.setMsg("No more data");
            mView.onQueryVideoResult(result);
        }
    }

    /**
     * 是否有更多视频
     * @return true 有更多，false 没有更多
     */
    public boolean hasMoreVideo() {
        return mHasMoreVideo;
    }


    /**
     * 查询视频
     * @param pageNo 页码
     */
    private void queryVideo(int pageNo) {
        mModel.queryVideo(pageNo, new HttpAsyncTask.Callback<QueryVideoResp>() {
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
