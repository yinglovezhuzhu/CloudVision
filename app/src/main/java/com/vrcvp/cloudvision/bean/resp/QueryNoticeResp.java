package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.NoticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询公告接口返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/25.
 */

public class QueryNoticeResp extends BaseResp<List<NoticeBean>> {


    public QueryNoticeResp() {
    }

    public QueryNoticeResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    @Override
    public String toString() {
        return "QueryNoticeResp{} " + super.toString();
    }
}
