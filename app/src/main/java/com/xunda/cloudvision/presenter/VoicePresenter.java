package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.IVoiceModel;
import com.xunda.cloudvision.model.VoiceModel;
import com.xunda.cloudvision.view.IVoiceView;

/**
 * 语音Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/17.
 */
public class VoicePresenter {

    private IVoiceView mVoiceView;
    private IVoiceModel mVoiceModel;

    public VoicePresenter(IVoiceView voiceView) {
        this.mVoiceView = voiceView;
        this.mVoiceModel = new VoiceModel();
    }

}
