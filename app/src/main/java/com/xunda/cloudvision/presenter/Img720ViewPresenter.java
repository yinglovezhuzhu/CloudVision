package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.IImg720ViewModel;
import com.xunda.cloudvision.model.Img720ViewModel;
import com.xunda.cloudvision.view.IImg720ViewView;

/**
 * 720度看图Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/29.
 */

public class Img720ViewPresenter {

    private IImg720ViewModel mModel;
    private IImg720ViewView mView;

    public Img720ViewPresenter(IImg720ViewView view) {
        this.mView = view;
        this.mModel = new Img720ViewModel();
    }
}
