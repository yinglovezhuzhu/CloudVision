package com.vrcvp.cloudvision.view;

import com.vrcvp.cloudvision.bean.VoiceBean;
import com.vrcvp.cloudvision.bean.VoiceSearchResultBean;
import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;

import java.util.List;

/**
 * 语音视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public interface IVoiceView extends IView {

    int ERROR_APPID_INVALID = 0;
    /** 语音合成（文字转语音）引擎初始化失败 **/
    int ERROR_SPEECH_SYNTHESIZER_INI_FAILED = 1;
    /** 语音识别（语音转文字）引擎初始化失败 **/
    int ERROR_SPEECH_RECOGNIZER_INI_FAILED = 2;

    /** 动作-无 **/
    int ACTION_NONE = 0;
//    /** 动作-语音播报 **/
//    int ACTION_SPEAK = 1;

    /**
     * 讯飞引擎错误
     * @param code 错误码
     * @param message 描述文字
     */
    void onXFEngineError(int code, String message);

    /**
     * @param bean 语音文字内容
     * @param action 动作，更新语音后需要做的操作
     */
    void onNewVoiceData(VoiceBean bean, int action);

//    void onSpeakBegin();
//
//    void onSpeakCompleted();

    void showLoadingDialog();

    void cancelLoadingDialog();

    /**
     * 语音搜索结果
     * @param result 结果数据
     */
    void onVoiceSearchResult(List<VoiceSearchResultBean> result);

    /**
     * 清除列表数据
     * @param keepFirst 是否保存第一个
     */
    void clearListView(boolean keepFirst);

    /**
     * 更新最后一个机器人列表数据（如果最后一个不是机器人数据，不操作）
     * @param text 新文字
     */
    void updateLastAndroid(String text);

    /**
     * 浏览网页地址
     * @param url 网页地址url
     */
    void viewWebURL(String url);
}
