package com.xunda.cloudvision.view;

/**
 * 产品列表视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IProductView {

    /**
     * 查询产品结果
     * @param result
     */
    void onQueryProductResult(RecommendedProductResp result);
}
