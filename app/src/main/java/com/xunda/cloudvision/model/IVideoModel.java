package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 云展视频Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public interface IVideoModel {

    /**
     * 查询视频
     * @param callback 回调
     */
    void queryVideo(final HttpAsyncTask.Callback<QueryVideoResp> callback);
}
