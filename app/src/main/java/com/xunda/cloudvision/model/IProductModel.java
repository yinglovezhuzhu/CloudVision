package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 产品Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public interface IProductModel {

    /**
     * 查询产品列表
     * @param pageNo 页码
     * @param callback 回调
     */
    void queryProduct(int pageNo, final HttpAsyncTask.Callback<QueryProductResp> callback);

}
