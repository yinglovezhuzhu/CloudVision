package com.vrcvp.cloudvision.bean.req;

import com.vrcvp.cloudvision.bean.AttrValueBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 查询商品价格的请求入参数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/24.
 */

public class QuerySkuPriceReq extends BaseReq {
    private String enterpriseId;
    private String productId;
    private List<AttrValueBean> list = new ArrayList<>();

    public QuerySkuPriceReq() {
    }

    public QuerySkuPriceReq(String token) {
        super(token);
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void addAttrValue(AttrValueBean attrValue) {
        if(null == attrValue) {
            return;
        }
        this.list.add(attrValue);
    }

    public void addAttrValues(Collection<AttrValueBean> attrValues) {
        if(null == attrValues || attrValues.isEmpty()) {
            return;
        }
        this.list.addAll(attrValues);
    }

    @Override
    public String toString() {
        return "QuerySkuPriceReq{" +
                "enterpriseId='" + enterpriseId + '\'' +
                ", productId='" + productId + '\'' +
                ", list=" + list +
                "} " + super.toString();
    }
}
