package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.QueryRecommendedProductResp;

/**
 * Corporate View
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public interface ICorporateView extends IView {

    void onQueryRecommendedProductResult(QueryRecommendedProductResp result);
}
