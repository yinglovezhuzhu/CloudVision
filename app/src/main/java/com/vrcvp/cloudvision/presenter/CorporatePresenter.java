package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.QueryCorporateResp;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.model.CorporateModel;
import com.vrcvp.cloudvision.model.ICorporateModel;
import com.vrcvp.cloudvision.view.ICorporateView;

/**
 * Corporate Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class CorporatePresenter {

    /** 异步请求Key：查询推荐商品 **/
    public static final String KEY_QUERY_RECOMMEND_PRODUCT = "query_recommend_product";
    /** 异步请求Key：查询企业信息 **/
    public static final String KEY_QUERY_CORPORATE_INFO = "query_corporate_info";

    private ICorporateView mView;
    private ICorporateModel mModel;

    public CorporatePresenter(Context context, ICorporateView view) {
        this.mView = view;
        this.mModel = new CorporateModel(context);
    }

    /**
     * 获取缓存的推荐产品列表数据
     */
    public QueryProductResp getCachedRecommendedProduct() {
        return null;
    }

    /**
     * 查询企业信息
     */
    public void queryCorporateInfo() {
        mModel.queryCorporateInfo(new HttpAsyncTask.Callback<QueryCorporateResp>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onResult(QueryCorporateResp result) {
                mView.onQueryCorporateInfoResult(result);
            }
        });
    }

    /**
     * 查询推荐产品列表
     */
    public void queryRecommendedProduct() {
        mModel.queryRecommendedProduct(new HttpAsyncTask.Callback<QueryProductResp>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onResult(QueryProductResp result) {
                mView.onQueryRecommendedProductResult(result);
            }
        });
    }

    public void queryRecommendVideo() {
        mModel.queryRecommendedVideo(new HttpAsyncTask.Callback<QueryVideoResp>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onResult(QueryVideoResp result) {
                mView.onQueryRecommendedVideoReseult(result);
            }
        });
    }
}
