package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.resp.QueryCorporateResp;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

/**
 * Corporate Model interface
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public interface ICorporateModel {

    /**
     * 查询企业信息
     * @param callback 回调
     */
    void queryCorporateInfo(HttpAsyncTask.Callback<QueryCorporateResp> callback);

    /**
     * 取消查询企业信息异步线程任务
     */
    void cancelQueryCorporateInfo();

    /**
     * 查询推荐商品
     * @param callback 回调
     */
    void queryRecommendedProduct(HttpAsyncTask.Callback<QueryProductResp> callback);

    /**
     * 取消查询推荐产品异步线程任务
     */
    void cancelQueryRecommendedProduct();

    /**
     * 查询推荐视频
     * @param callback 回调
     */
    void queryRecommendedVideo(HttpAsyncTask.Callback<QueryVideoResp> callback);

    /**
     * 取消查询推荐视频异步线程任务
     */
    void cancelQueryRecommendedVideo();
}
