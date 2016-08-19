package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.IRegisterModel;
import com.xunda.cloudvision.view.IRegisterView;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class RegisterPresenter extends BasePresenter {

    private IRegisterView mRegisterView;
    private IRegisterModel mRegisterModel;

    public RegisterPresenter(IRegisterView registerView) {
        this.mRegisterView = registerView;
    }

}
