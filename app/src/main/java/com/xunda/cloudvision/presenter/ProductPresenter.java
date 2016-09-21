package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.IProductModel;
import com.xunda.cloudvision.model.ProductModel;
import com.xunda.cloudvision.view.IProductView;

/**
 * 产品列表Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductPresenter {

    private IProductView mView;
    private IProductModel mModel;

    public ProductPresenter(IProductView view) {
        this.mView = view;
        this.mModel = new ProductModel();
    }
}
