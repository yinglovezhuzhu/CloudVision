package com.xunda.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Sku数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class SkuBean implements Parcelable{
    private String price;
    private List<AttrValueBean> attrValue = new ArrayList<>();

    public SkuBean() {

    }

    protected SkuBean(Parcel in) {
        price = in.readString();
        attrValue = in.createTypedArrayList(AttrValueBean.CREATOR);
    }

    public static final Creator<SkuBean> CREATOR = new Creator<SkuBean>() {
        @Override
        public SkuBean createFromParcel(Parcel in) {
            return new SkuBean(in);
        }

        @Override
        public SkuBean[] newArray(int size) {
            return new SkuBean[size];
        }
    };

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<AttrValueBean> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<AttrValueBean> attrValue) {
        this.attrValue = attrValue;
    }

    @Override
    public String toString() {
        return "SkuBean{" +
                "price='" + price + '\'' +
                ", attrValue=" + attrValue +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeTypedList(attrValue);
    }
}
