package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.QueryProductResp;

/**
 * 产品列表视图接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IProductView extends IView {

    /**
     * 查询产品结果
     * @param result
     */
    void onQueryProductResult(QueryProductResp result);
}
