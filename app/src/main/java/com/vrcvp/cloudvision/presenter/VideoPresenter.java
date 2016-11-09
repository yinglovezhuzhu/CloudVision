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

    private int mPageNo = 1;
    private boolean mHasMore = true;
    private boolean mLoadMore = false;

    public VideoPresenter(Context context, IVideoView view) {
        this.mView = view;
        this.mModel = new VideoModel(context);
    }
    /**
     * 查询第一页数据
     */
    public void queryVideoFirstPage() {
        mPageNo = 1;
        mLoadMore = false;
        queryVideo();
    }

    /**
     * 查询下一页数据
     */
    public void queryVideoNextPage() {
        if(mHasMore) {
            mPageNo++;
            mLoadMore = true;
            queryVideo();
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
    public boolean hasMore() {
        return mHasMore;
    }


    /**
     * 是否加载更多操作
     * @return 是否加载更多操作， true 是， false 否
     */
    public boolean isLoadMore() {
        return mLoadMore;
    }

    public void cancelQueryVideo() {
        mModel.cancelQueryVideo();
    }

    public void onDestroy() {
        cancelQueryVideo();
    }

    /**
     * 查询视频
     */
    private void queryVideo() {
        mModel.queryVideo(mPageNo, new HttpAsyncTask.Callback<QueryVideoResp>() {
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
                if(null == result) {
                    mHasMore = false;
                    if(mPageNo > 1) {
                        result = new QueryVideoResp();
                        result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                        result.setMsg("No more data");
                    }
                    mPageNo--;
                } else {
                    mHasMore = null != result.getData() && !result.getData().isEmpty();
                    if(!mHasMore && mPageNo > 1) {
                        result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                        result.setMsg("No more data");
                    }
                    mPageNo = HttpStatus.SC_OK == result.getHttpCode() ? mPageNo : mPageNo--;
                }
                mView.onQueryVideoResult(result);
            }
        });
    }
}
