package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 产品搜索Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public interface IProductSearchModel {

    /**
     * 搜索产品Model接口
     * @param keyword 关键字
     * @param pageNo 页码
     * @param callback 回调
     */
    void searchProduct(String keyword, int pageNo, final HttpAsyncTask.Callback<QueryProductResp> callback);
}
