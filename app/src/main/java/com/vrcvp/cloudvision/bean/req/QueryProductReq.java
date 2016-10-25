package com.vrcvp.cloudvision.bean.req;

/**
 * 查询商品接口（查询推荐商品，查询商品）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryProductReq extends PageReq {

    private String enterpriseId;

    public QueryProductReq() {
    }

    public QueryProductReq(String token) {
        super(token);
    }

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
