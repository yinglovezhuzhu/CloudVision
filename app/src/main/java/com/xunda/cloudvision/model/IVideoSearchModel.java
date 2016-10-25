package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryVideoResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 云展视频搜索Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public interface IVideoSearchModel {
    /**
     * 搜索视频
     * @param keyword 关键字
     * @param pageNo 页码
     * @param callback 回调
     */
    void searchVideo(String keyword, int pageNo, final HttpAsyncTask.Callback<QueryVideoResp> callback);
}
