package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.IActivateModel;
import com.xunda.cloudvision.model.ActivateModel;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IRegisterView;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class ActivatePresenter {

    private IRegisterView mRegisterView;
    private IActivateModel mRegisterModel;

    public ActivatePresenter(IRegisterView registerView) {
        this.mRegisterView = registerView;
        this.mRegisterModel = new ActivateModel();
    }

    public void register() {
        final String code = mRegisterView.getCodeText();
        if(StringUtils.isEmpty(code)) {
            mRegisterView.onCodeEmpty();
            return;
        }
        mRegisterModel.register(code);
    }
}
