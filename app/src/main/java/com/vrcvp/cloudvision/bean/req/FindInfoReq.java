package com.vrcvp.cloudvision.bean.req;

/**
 * 查找广告机信息请求参数实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/23.
 */

public class FindInfoReq extends BaseReq {

    private String enterpriseId;

    public FindInfoReq() {
    }

    public FindInfoReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "FindInfoReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
