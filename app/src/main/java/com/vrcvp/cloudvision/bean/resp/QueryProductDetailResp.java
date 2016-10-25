package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.ProductBean;

/**
 * 查询商品详情信息接口返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryProductDetailResp extends BaseResp {
    private ProductBean product;

    public QueryProductDetailResp() {
    }

    public QueryProductDetailResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public ProductBean getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "QueryProductDetailResp{" +
                "product=" + product +
                "} " + super.toString();
    }
}
