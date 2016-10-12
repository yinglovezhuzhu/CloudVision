package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 产品搜索Model
 * Created by yinglovezhuzhu@gmail.com on 2016/9/28.
 */

public interface IProductSearchModel {

    void searchProduct(String keyword, final HttpAsyncTask.Callback<QueryProductResp> callback);
}
