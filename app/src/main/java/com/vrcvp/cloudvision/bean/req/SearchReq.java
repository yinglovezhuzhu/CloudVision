package com.vrcvp.cloudvision.bean.req;

/**
 * 搜索接口入参数据实体类（产品搜索，视频搜索）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class SearchReq extends PageReq {
    private String enterpriseId;
    private String keywords;	// 关键词

    public SearchReq() {
    }

    public SearchReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "SearchReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                ", keywords='" + keywords + '\'' +
                "} " + super.toString();
    }
}
