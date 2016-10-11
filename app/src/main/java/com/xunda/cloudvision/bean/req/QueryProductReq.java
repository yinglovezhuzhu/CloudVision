package com.xunda.cloudvision.bean.req;

/**
 * 查询商品接口（查询推荐商品，查询商品）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryProductReq extends BaseReq {

    private String enterpriseId;

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryProductReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
