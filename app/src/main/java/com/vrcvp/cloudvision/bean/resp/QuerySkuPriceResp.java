package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.SkuPrice;

/**
 * 查询商品价格的请求返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/24.
 */

public class QuerySkuPriceResp extends BaseResp<SkuPrice> {


    public QuerySkuPriceResp() {
    }

    public QuerySkuPriceResp(int httpCode, String msg) {
        super(httpCode, msg);
    }


    @Override
    public String toString() {
        return "QuerySkuPriceResp{} " + super.toString();
    }
}
