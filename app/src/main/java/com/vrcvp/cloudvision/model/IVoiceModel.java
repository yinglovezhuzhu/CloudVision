package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

/**
 * 语音Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public interface IVoiceModel {
    /**
     * 搜索视频
     * @param keyword 关键字
     * @param pageNo 页码
     * @param callback 回调
     */
    void searchVoiceRequest(String keyword, int pageNo, final HttpAsyncTask.Callback<VoiceSearchResp> callback);

    /**
     * 取消搜索视频的异步线程任务
     */
    void cancelSearchVoiceRequest();
}
