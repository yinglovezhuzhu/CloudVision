package com.xunda.cloudvision.bean.resp;

import com.xunda.cloudvision.bean.CorporateBean;
import com.xunda.cloudvision.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询视频接口返回数据实体类（查询视频接口，视频搜索接口）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryCorporateResp extends BaseResp{

    private CorporateBean enterprise;

    public CorporateBean getEnterprise() {
        return enterprise;
    }

    @Override
    public String toString() {
        return "QueryCorporateResp{" +
                "enterprise=" + enterprise +
                "} " + super.toString();
    }
}
