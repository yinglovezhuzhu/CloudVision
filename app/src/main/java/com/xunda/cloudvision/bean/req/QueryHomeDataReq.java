package com.xunda.cloudvision.bean.req;

/**
 * 查询首页信息接口入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryHomeDataReq extends BaseReq {
    private String enterpriseId;

    public QueryHomeDataReq() {
    }

    public QueryHomeDataReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryHomeDataReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
