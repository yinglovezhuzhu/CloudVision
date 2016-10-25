package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

/**
 * 云展视频Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public interface IVideoModel {

    /**
     * 查询视频
     * @param pageNo 页码
     * @param callback 回调
     */
    void queryVideo(int pageNo, final HttpAsyncTask.Callback<QueryVideoResp> callback);
}
