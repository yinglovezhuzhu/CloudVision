package com.vrcvp.cloudvision.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 产品属性实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/26.
 */

public class AttrBean {
    private String attrId;
    private String attrName;
    private List<AttrValueBean> values = new ArrayList<>();

    public AttrBean() {

    }

    public AttrBean(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrId() {
        return attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public List<AttrValueBean> getValues() {
        return values;
    }

    public void addAttrValue(AttrValueBean attrValue) {
        if(null == attrValue) {
            return;
        }
        if(null == values) {
            values = new ArrayList<>();
        }
        values.add(attrValue);
    }

    public void addAttrValues(Collection<AttrValueBean> attrValues) {
        if(null == attrValues || attrValues.isEmpty()) {
            return;
        }
        if(null == values) {
            values = new ArrayList<>();
        }
        values.addAll(attrValues);
    }

    @Override
    public String toString() {
        return "AttrBean{" +
                "attrId='" + attrId + '\'' +
                ", attrName='" + attrName + '\'' +
                ", values=" + values +
                '}';
    }
}
