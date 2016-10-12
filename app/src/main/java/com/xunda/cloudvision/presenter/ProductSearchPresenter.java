package com.xunda.cloudvision.presenter;

import android.content.Context;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.model.IProductSearchModel;
import com.xunda.cloudvision.model.ProductSearchModel;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IProductSearchView;

/**
 * 产品搜索Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class ProductSearchPresenter {

    private IProductSearchView mView;
    private IProductSearchModel mModel;

    public ProductSearchPresenter(Context context, IProductSearchView view) {
        this.mView = view;
        mModel = new ProductSearchModel(context);
    }

    public void search() {
        String keyword = mView.getKeyword();
        if(StringUtils.isEmpty(keyword)) {
            mView.onKeywordEmptyError();
            return;
        }
        mModel.searchProduct(keyword, new HttpAsyncTask.Callback<QueryProductResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mView.onCanceled(null);
            }

            @Override
            public void onResult(QueryProductResp result) {
                mView.onSearchProductResult(result);
            }
        });
    }
}
