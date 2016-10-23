package com.xunda.cloudvision.view;

import com.xunda.cloudvision.bean.resp.QueryCorporateResp;
import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.bean.resp.QueryVideoResp;

/**
 * Corporate View
 * Created by yinglovezhuzhu@gmail.com on 2016/9/19.
 */
public interface ICorporateView {

    /**
     * 异步执行前
     *
     * @param key 关键字，用来区分是哪个异步线程
     */
    void onPreExecute(String key);

    /**
     * 取消异步任务
     *
     * @param key 关键字，用来区分是哪个异步线程
     */
    void onCanceled(String key);

    /**
     * 查询企业详情结果
     * @param result 企业详情
     */
    void onQueryCorporateInfoResult(QueryCorporateResp result);

    /**
     * 查询推荐商品信息结果
     * @param result 推荐商品信息查询结果数据
     */
    void onQueryRecommendedProductResult(QueryProductResp result);

    /**
     * 查询推荐视频结果
     * @param result 推荐视频列表数据
     */
    void onQueryRecommendedVideoReseult(QueryVideoResp result);
}
