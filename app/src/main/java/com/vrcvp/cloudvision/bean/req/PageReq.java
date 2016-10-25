package com.vrcvp.cloudvision.bean.req;

/**
 * 分页请求入参实体类基类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/25.
 */

public class PageReq extends BaseReq {

    private int pageNo = 1;

    public PageReq() {
    }

    public PageReq(String token) {
        super(token);
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "pageNo=" + pageNo +
                "} " + super.toString();
    }
}
