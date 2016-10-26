package com.vrcvp.cloudvision.bean.resp;

/**
 * 查询商品价格的请求返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/24.
 */

public class QuerySkuPriceResp extends BaseResp {
    private String price;

    public QuerySkuPriceResp() {
    }

    public QuerySkuPriceResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "QuerySkuPriceResp{" +
                "price='" + price + '\'' +
                "} " + super.toString();
    }
}
