package com.vrcvp.cloudvision.view;

/**
 * 语音视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public interface IVoiceView {

    int ERROR_APPID_INVALID = 0;
    /** 语音引擎初始化失败 **/
    int ERROR_SPEECH_INI_FAILED = 1;
    /** 语音合成（文字转语音）引擎初始化失败 **/
    int ERROR_SPEECH_SYNTHESIZER_INI_FAILED = 2;
    /** 语音识别（语音转文字）引擎初始化失败 **/
    int ERROR_SPEECH_RECOGNIZER_INI_FAILED = 3;

    int ACTION_NONE = 0;

    /**
     * 讯飞引擎错误
     * @param code 错误码
     * @param message 描述文字
     */
    void onXFEngineError(int code, String message);

    /**
     * 新的语音数据
     * @param type 类型 {@linkplain com.vrcvp.cloudvision.bean.VoiceBean#TYPE_ANDROID}
     *             或者{@linkplain com.vrcvp.cloudvision.bean.VoiceBean#TYPE_HUMAN}
     * @param text 语音文字内容
     * @param action 动作，更新语音后需要做的操作
     */
    void onNewVoiceData(int type, String text, int action);
}
