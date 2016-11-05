package com.vrcvp.cloudvision.bean.req;

/**
 * 查询公告请求入参数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/5.
 */

public class QueryNoticeReq extends PageReq {
    private String enterpriseId;

    public QueryNoticeReq() {
    }

    public QueryNoticeReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "QueryNoticeReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                "} " + super.toString();
    }
}
