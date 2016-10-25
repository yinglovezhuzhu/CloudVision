package com.vrcvp.cloudvision.view;

import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;

/**
 * 云展视频搜索View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public interface IVideoSearchView extends ISearchView {

    /**
     * 搜索视频结果
     * @param result
     */
    void onSearchVideoResult(QueryVideoResp result);
}
