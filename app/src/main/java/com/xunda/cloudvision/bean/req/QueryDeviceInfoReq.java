package com.xunda.cloudvision.bean.req;

/**
 * 查询设备信息接口入参实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryDeviceInfoReq extends BaseReq {
    private String enterpriseId;

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryDeviceInfoReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
