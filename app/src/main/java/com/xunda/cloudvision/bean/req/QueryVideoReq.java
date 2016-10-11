package com.xunda.cloudvision.bean.req;

/**
 * 查询视频接口
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryVideoReq extends BaseReq {

    private String enterpriseId;

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryVideoReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
