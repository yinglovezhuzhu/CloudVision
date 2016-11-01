package com.vrcvp.cloudvision.bean.req;

/**
 * 查询首页信息接口入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryAdvertiseReq extends BaseReq {
    private String enterpriseId;

    public QueryAdvertiseReq() {
    }

    public QueryAdvertiseReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryAdvertiseReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
