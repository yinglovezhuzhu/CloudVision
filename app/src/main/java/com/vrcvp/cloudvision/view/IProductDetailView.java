package com.vrcvp.cloudvision.view;

import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;
import com.vrcvp.cloudvision.bean.resp.QuerySkuPriceResp;

/**
 * 产品详情View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/27.
 */

public interface IProductDetailView extends IView {

    /**
     * 查询产品结果
     * @param result 结果数据
     */
    void onQueryProductDetailResult(QueryProductDetailResp result);

    /**
     * 查询Sku价格
     * @param result 结果数据
     */
    void onQuerySkuPriceResult(QuerySkuPriceResp result);
}
