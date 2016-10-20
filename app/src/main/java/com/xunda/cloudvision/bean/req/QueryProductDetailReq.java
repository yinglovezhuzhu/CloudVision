package com.xunda.cloudvision.bean.req;

/**
 * 查询商品详情入参数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryProductDetailReq extends BaseReq {
    private String productId;

    public QueryProductDetailReq() {
    }

    public QueryProductDetailReq(String token) {
        super(token);
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "QueryProductDetailReq{" +
                "productId='" + productId + '\'' +
                "} " + super.toString();
    }
}
