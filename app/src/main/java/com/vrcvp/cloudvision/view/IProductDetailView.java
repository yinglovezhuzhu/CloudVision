package com.vrcvp.cloudvision.view;

import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;

/**
 * 产品详情View接口
 * Created by yinglovezhuzhu@gmail.com on 2016/9/27.
 */

public interface IProductDetailView extends IView {

    /**
     * 查询产品结果
     * @param result
     */
    void onQueryProductDetailResult(QueryProductDetailResp result);
}
