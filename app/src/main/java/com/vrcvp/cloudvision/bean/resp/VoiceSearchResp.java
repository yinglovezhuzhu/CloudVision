package com.vrcvp.cloudvision.bean.resp;

/**
 * 语音搜索结果返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/11.
 */

public class VoiceSearchResp extends BaseResp<VoiceSearchResp.VoiceSearchData> {

    public VoiceSearchResp() {
    }

    public VoiceSearchResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public static class VoiceSearchData {

    }
}
