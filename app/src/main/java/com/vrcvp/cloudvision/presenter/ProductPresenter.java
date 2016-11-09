package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
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

    private int mPageNo = 1;
    private boolean mHasMore = true;
    private boolean mLoadMore = false;

    public ProductPresenter(Context context, IProductView view) {
        this.mView = view;
        this.mModel = new ProductModel(context);
    }

    /**
     * 查询第一页数据
     */
    public void queryProductFirstPage() {
        mPageNo = 1;
        mLoadMore = false;
        queryProduct(mPageNo);
    }

    /**
     * 查询下一页数据
     */
    public void queryProductNextPage() {
        if(mHasMore) {
            mPageNo++;
            mLoadMore = true;
            queryProduct(mPageNo);
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
    public boolean hasMore() {
        return mHasMore;
    }

    /**
     * 是否加载更多操作
     * @return 是否加载更多操作， true 是， false 否
     */
    public boolean isLoadMore() {
        return mLoadMore;
    }

    public void cancelQueryProduct() {
        mModel.cancelQueryProduct();
    }

    public void onDestroy() {
        cancelQueryProduct();
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
                mPageNo--;
                mView.onCanceled("");
            }

            @Override
            public void onResult(QueryProductResp result) {
                if(null == result) {
                    mHasMore = false;
                    if(mPageNo > 1) {
                        result = new QueryProductResp();
                        result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                        result.setMsg("No more data");
                    }
                    mPageNo--;
                } else {
                    mHasMore = null != result.getData() && !result.getData().isEmpty();
                    if(!mHasMore && mPageNo > 1) {
                        result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                        result.setMsg("No more data");
                    }
                    mPageNo = HttpStatus.SC_OK == result.getHttpCode() ? mPageNo : mPageNo--;
                }
                mView.onQueryProductResult(result);
            }
        });
    }
}
