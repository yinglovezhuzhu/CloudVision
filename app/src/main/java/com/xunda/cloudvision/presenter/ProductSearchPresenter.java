package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.model.IProductSearchModel;
import com.xunda.cloudvision.model.ProductSearchModel;
import com.xunda.cloudvision.view.IProductSearchView;

/**
 * 产品搜索Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class ProductSearchPresenter {

    private IProductSearchView mView;
    private IProductSearchModel mModel;

    public ProductSearchPresenter(IProductSearchView view) {
        this.mView = view;
        mModel = new ProductSearchModel();
    }
}
