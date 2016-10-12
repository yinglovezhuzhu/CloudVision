package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.QueryProductDetailResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 产品详情Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/10/12.
 */

public interface IProductDetailModel {

    void queryProductDetail(String productId, final HttpAsyncTask.Callback<QueryProductDetailResp> callback);
}
