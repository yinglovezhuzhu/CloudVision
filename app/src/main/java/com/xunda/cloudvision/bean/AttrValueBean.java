package com.xunda.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品属性数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class AttrValueBean implements Parcelable {

    private String attrId; // 属性名id
    private String attrName; // 属性名
    private String attrValueId; // 属性值id
    private String attrValue; // 属性值

    public AttrValueBean() {

    }

    public AttrValueBean(String name) {
        this.attrName = name;
    }

    protected AttrValueBean(Parcel in) {
        attrId = in.readString();
        attrName = in.readString();
        attrValueId = in.readString();
        attrValue = in.readString();
    }

    public static final Creator<AttrValueBean> CREATOR = new Creator<AttrValueBean>() {
        @Override
        public AttrValueBean createFromParcel(Parcel in) {
            return new AttrValueBean(in);
        }

        @Override
        public AttrValueBean[] newArray(int size) {
            return new AttrValueBean[size];
        }
    };

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(String attrValueId) {
        this.attrValueId = attrValueId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    @Override
    public String toString() {
        return "AttrValueBean{" +
                "attrId='" + attrId + '\'' +
                ", attrName='" + attrName + '\'' +
                ", attrValueId='" + attrValueId + '\'' +
                ", attrValue='" + attrValue + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attrId);
        dest.writeString(attrName);
        dest.writeString(attrValueId);
        dest.writeString(attrValue);
    }
}
