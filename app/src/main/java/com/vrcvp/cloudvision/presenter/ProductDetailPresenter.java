package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.model.IProductDetailModel;
import com.vrcvp.cloudvision.model.ProductDetailModel;
import com.vrcvp.cloudvision.view.IProductDetailView;

/**
 * 产品详情Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/10/12.
 */

public class ProductDetailPresenter {
    private IProductDetailView mView;
    private IProductDetailModel mModel;

    public ProductDetailPresenter(Context context, IProductDetailView view) {
        this.mView = view;
        this.mModel = new ProductDetailModel(context);
    }

    public void queryProductDetail(String productId) {
        mModel.queryProductDetail(productId, new HttpAsyncTask.Callback<QueryProductDetailResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mView.onCanceled(null);
            }

            @Override
            public void onResult(QueryProductDetailResp result) {
                mView.onQueryProductDetailResult(result);
            }
        });
    }
}
