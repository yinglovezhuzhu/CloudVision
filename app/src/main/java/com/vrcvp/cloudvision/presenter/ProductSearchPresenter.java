package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.model.IProductSearchModel;
import com.vrcvp.cloudvision.model.ProductSearchModel;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.IProductSearchView;

/**
 * 产品搜索Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public class ProductSearchPresenter {

    private IProductSearchView mView;
    private IProductSearchModel mModel;

    private String mKeyword;
    private int mPage = 1;
    private boolean mHasMore = true;

    public ProductSearchPresenter(Context context, IProductSearchView view) {
        this.mView = view;
        mModel = new ProductSearchModel(context);
    }

    /**
     * 搜索
     */
    public void search() {
        final String keyword = mView.getKeyword();
        if(StringUtils.isEmpty(mKeyword) && StringUtils.isEmpty(keyword)) {
            mView.onKeywordEmptyError();
            return;
        }
        mKeyword = keyword;
        mPage = 1;
        loadData(mPage);
    }

    /**
     * 下一页
     */
    public void nextPage() {
        mPage++;
        loadData(mPage);
    }

    /**
     * 是否有更多数据
     * @return true 有更多数据， false 没有更多数据
     */
    public boolean hasMore() {
        return mHasMore;
    }

    /**
     * 取消任务
     */
    public void cancelLoadDataTask() {
        mModel.cancelSearchProduct();
    }

    /**
     *
     */
    public void onDestroy() {
        cancelLoadDataTask();
    }

    /**
     * 加载数据
     * @param pageNo 页码
     */
    private void loadData(int pageNo) {
        mModel.searchProduct(mKeyword, pageNo, new HttpAsyncTask.Callback<QueryProductResp>() {
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
                if(null == result) {
                    mHasMore = false;
                    if(mPage > 1) {
                        result = new QueryProductResp();
                        result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                        result.setMsg("No more data");
                    }
                    mPage--;
                } else {
                    mHasMore = null != result.getData() && !result.getData().isEmpty();
                    if(!mHasMore && mPage > 1) {
                        result.setHttpCode(HttpStatus.SC_NO_MORE_DATA);
                        result.setMsg("No more data");
                    }
                    mPage = HttpStatus.SC_OK == result.getHttpCode() ? mPage : mPage--;
                }
                mView.onSearchProductResult(result);
            }
        });
    }
}
