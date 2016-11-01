package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.AdvertiseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询广告机信息接口返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryAdvertiseResp extends BaseResp<List<AdvertiseBean>> {

    public QueryAdvertiseResp() {
    }

    public QueryAdvertiseResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    @Override
    public String toString() {
        return "QueryAdvertiseResp{} " + super.toString();
    }
}
