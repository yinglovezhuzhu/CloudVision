package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.CorporateModel;
import com.xunda.cloudvision.model.ICorporateModel;
import com.xunda.cloudvision.view.ICorporateView;

/**
 * Corporate Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class CorporatePresenter {

    private ICorporateView mView;
    private ICorporateModel mModel;

    public CorporatePresenter(ICorporateView view) {
        this.mView = view;
        this.mModel = new CorporateModel();
    }
}
