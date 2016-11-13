package com.vrcvp.cloudvision.view;

import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;

/**
 * 语音搜索View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/11/13.
 */

public interface IVoiceSearchView extends IView {

    void onVoiceSearchResult(VoiceSearchResp result);
}
