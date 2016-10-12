package com.xunda.cloudvision.presenter;

import android.content.Context;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
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

    public ProductPresenter(Context context, IProductView view) {
        this.mView = view;
        this.mModel = new ProductModel(context);
    }

    public void queryProduct() {
        mModel.queryProduct(new HttpAsyncTask.Callback<QueryProductResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute("");
            }

            @Override
            public void onCanceled() {
                mView.onCanceled("");
            }

            @Override
            public void onResult(QueryProductResp result) {
                mView.onQueryProductResult(result);
            }
        });
    }
}
