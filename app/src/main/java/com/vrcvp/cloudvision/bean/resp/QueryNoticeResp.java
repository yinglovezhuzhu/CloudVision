package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.NoticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询公告接口返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/25.
 */

public class QueryNoticeResp extends BaseResp {

    private List<NoticeBean> notices = new ArrayList<>();

    public QueryNoticeResp() {
    }

    public QueryNoticeResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public List<NoticeBean> getNotices() {
        return notices;
    }

    @Override
    public String toString() {
        return "QueryNoticeResp{" +
                "notices=" + notices +
                "} " + super.toString();
    }
}
