package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.AttrValueBean;
import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;
import com.vrcvp.cloudvision.bean.resp.QuerySkuPriceResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

import java.util.Collection;

/**
 * 产品详情Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/10/12.
 */

public interface IProductDetailModel {

    /**
     * 查询商品详情
     * @param productId 产品id
     * @param callback 回调
     */
    void queryProductDetail(String productId, final HttpAsyncTask.Callback<QueryProductDetailResp> callback);

    /**
     * 查询Sku价格
     * @param productId 产品id
     * @param attrValueList 属性组合列表
     * @param callback 回调
     */
    void querySkuPrice(String productId, Collection<AttrValueBean> attrValueList,
                       HttpAsyncTask.Callback<QuerySkuPriceResp> callback);
}
