package com.xunda.cloudvision.presenter;

import android.content.Context;

import com.xunda.cloudvision.model.IRegisterModel;
import com.xunda.cloudvision.model.RegisterModel;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IRegisterView;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class RegisterPresenter extends BasePresenter {

    private IRegisterView mRegisterView;
    private IRegisterModel mRegisterModel;

    public RegisterPresenter(Context context, IRegisterView registerView) {
        super(context);
        this.mRegisterView = registerView;
        this.mRegisterModel = new RegisterModel();
    }

    public void register() {
        final String code = mRegisterView.getCodeText();
        if(StringUtils.isEmpty(code)) {

        }
    }
}
