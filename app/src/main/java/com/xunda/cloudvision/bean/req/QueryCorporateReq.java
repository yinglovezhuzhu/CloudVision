package com.xunda.cloudvision.bean.req;

/**
 * 查询企业信息接口入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryCorporateReq extends BaseReq {
    private String enterpriseId;

    public QueryCorporateReq() {
    }

    public QueryCorporateReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryCorporateReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
