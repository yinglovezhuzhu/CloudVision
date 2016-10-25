package com.vrcvp.cloudvision.bean.req;

/**
 * 请求入参数据实体类基类
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
class BaseReq {
    private String token;

    public BaseReq() {

    }

    public BaseReq(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseReq{" +
                "token='" + token + '\'' +
                '}';
    }
}
