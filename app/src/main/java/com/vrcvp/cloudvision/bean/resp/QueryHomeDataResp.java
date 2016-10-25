package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.AdvertiseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询广告机信息接口返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryHomeDataResp extends BaseResp {

    private List<AdvertiseBean> advertise = new ArrayList<>();

    public QueryHomeDataResp() {
    }

    public QueryHomeDataResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public List<AdvertiseBean> getAdvertise() {
        return advertise;
    }

    @Override
    public String toString() {
        return "QueryHomeDataResp{" +
                "advertise=" + advertise +
                "} " + super.toString();
    }
}
