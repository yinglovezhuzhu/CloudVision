package com.xunda.cloudvision.view;

/**
 * 语音视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public interface IVoiceView {

    /**
     * 新的语音数据
     * @param type 类型 {@linkplain com.xunda.cloudvision.bean.VoiceBean#TYPE_ANDROID}
     *             或者{@linkplain com.xunda.cloudvision.bean.VoiceBean#TYPE_HUMAN}
     * @param text 语音文字内容
     * @param action 动作，更新语音后需要做的操作
     */
    void onNewVoiceData(int type, String text, int action);
}
