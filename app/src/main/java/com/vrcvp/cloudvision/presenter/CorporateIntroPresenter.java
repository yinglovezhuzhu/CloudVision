package com.vrcvp.cloudvision.presenter;

import com.vrcvp.cloudvision.model.CorporateIntroModel;
import com.vrcvp.cloudvision.model.ICorporateIntroModel;
import com.vrcvp.cloudvision.view.ICorporateIntroView;

/**
 * Corporate Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class CorporateIntroPresenter {

    private ICorporateIntroView mView;
    private ICorporateIntroModel mModel;

    public CorporateIntroPresenter(ICorporateIntroView view) {
        this.mView = view;
        this.mModel = new CorporateIntroModel();
    }
}
