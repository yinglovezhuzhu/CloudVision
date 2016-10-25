package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.model.IProductModel;
import com.vrcvp.cloudvision.model.ProductModel;
import com.vrcvp.cloudvision.view.IProductView;

/**
 * 产品列表Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductPresenter {

    private IProductView mView;
    private IProductModel mModel;

    private int mProductPage = 1;
    private boolean mHasMoreProduct = true;

    public ProductPresenter(Context context, IProductView view) {
        this.mView = view;
        this.mModel = new ProductModel(context);
    }

    /**
     * 查询第一页数据
     */
    public void queryProductFirstPage() {
        mProductPage = 1;
        queryProduct(mProductPage);
    }

    /**
     * 查询下一页数据
     */
    public void queryProductNextPage() {
        if(mHasMoreProduct) {
            mProductPage++;
            queryProduct(mProductPage);
        } else {
            QueryProductResp result = new QueryProductResp();
            result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
            result.setMsg("No more data");
            mView.onQueryProductResult(result);
        }
    }

    /**
     * 是否有更多商品
     * @return true 有更多，false 没有更多
     */
    public boolean hasMoreProduct() {
        return mHasMoreProduct;
    }

    /**
     * 查询产品列表
     * @param pageNo 页码
     */
    private void queryProduct(int pageNo) {
        mModel.queryProduct(pageNo, new HttpAsyncTask.Callback<QueryProductResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute("");
            }

            @Override
            public void onCanceled() {
                mProductPage--;
                mView.onCanceled("");
            }

            @Override
            public void onResult(QueryProductResp result) {
                if(null == result) {
                    mHasMoreProduct = false;
                    mProductPage--;
                } else {
                    mHasMoreProduct = null != result.getProduct() && !result.getProduct().isEmpty();
                    mProductPage = HttpStatus.SC_OK == result.getHttpCode() ? mProductPage : mProductPage--;
                }
                if(!mHasMoreProduct) {
                    if(null == result) {
                        result = new QueryProductResp();
                    }
                    result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                    result.setMsg("No more data");
                }
                mView.onQueryProductResult(result);
            }
        });
    }
}
