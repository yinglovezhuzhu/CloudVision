package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询商品列表接口返回数据实体类（查询推荐商品，查询商品，产品搜索接口）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryProductResp extends BaseResp{

    private List<ProductBean> product = new ArrayList<>();

    public QueryProductResp() {
    }

    public QueryProductResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "QueryProductResp{" +
                "product=" + product +
                "} " + super.toString();
    }
}
