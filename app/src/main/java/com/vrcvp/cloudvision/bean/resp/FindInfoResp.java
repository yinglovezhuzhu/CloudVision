package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.InfoBean;

/**
 * 查询广告机信息返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/23.
 */

public class FindInfoResp extends BaseResp<InfoBean> {

    public FindInfoResp() {
    }

    public FindInfoResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    @Override
    public String toString() {
        return "FindInfoResp{} " + super.toString();
    }
}
