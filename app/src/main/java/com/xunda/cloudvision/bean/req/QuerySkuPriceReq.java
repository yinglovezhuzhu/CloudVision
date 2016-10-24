package com.xunda.cloudvision.bean.req;

/**
 * 查询商品价格的请求入参数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/24.
 */

public class QuerySkuPriceReq extends BaseReq {
    private String productId;
    private String attrValue;

    public QuerySkuPriceReq() {
    }

    public QuerySkuPriceReq(String token) {
        super(token);
    }
}
