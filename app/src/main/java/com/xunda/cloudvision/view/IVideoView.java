package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.QueryVideoResp;

/**
 * 云展视频View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public interface IVideoView extends IView {
    /**
     * 查询视频结果
     * @param result
     */
    void onQueryVideoResult(QueryVideoResp result);
}
