package com.xunda.cloudvision.presenter;

import android.content.Context;

import com.xunda.cloudvision.bean.resp.RecommendedProductResp;
import com.xunda.cloudvision.model.CorporateModel;
import com.xunda.cloudvision.model.ICorporateModel;
import com.xunda.cloudvision.view.ICorporateView;

/**
 * Corporate Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public class CorporatePresenter {

    private ICorporateView mView;
    private ICorporateModel mModel;

    public CorporatePresenter(Context context, ICorporateView view) {
        this.mView = view;
        this.mModel = new CorporateModel(context);
    }

    /**
     * 获取缓存的推荐产品列表数据
     */
    public RecommendedProductResp getCachedRecommendedProduct() {
        return null;
    }

    /**
     * 查询推荐产品列表
     */
    public void queryRecommendedProduct() {

    }
}
