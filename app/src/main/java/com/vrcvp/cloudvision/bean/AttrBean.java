package com.vrcvp.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 产品属性实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/26.
 */

public class AttrBean implements Parcelable{
    private String attrId;
    private String attrName;
    private List<AttrValueBean> values = new ArrayList<>();

    public AttrBean() {

    }

    public AttrBean(String attrId, String attrName) {
        this.attrId = attrId;
        this.attrName = attrName;
    }

    protected AttrBean(Parcel in) {
        attrId = in.readString();
        attrName = in.readString();
        values = in.createTypedArrayList(AttrValueBean.CREATOR);
    }

    public static final Creator<AttrBean> CREATOR = new Creator<AttrBean>() {
        @Override
        public AttrBean createFromParcel(Parcel in) {
            return new AttrBean(in);
        }

        @Override
        public AttrBean[] newArray(int size) {
            return new AttrBean[size];
        }
    };

    public String getAttrId() {
        return attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public List<AttrValueBean> getValues() {
        return values;
    }

    public AttrValueBean getValue(int position) {
        if(null == values || values.isEmpty() || position >= values.size()) {
            return null;
        }
        return values.get(position);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attrId);
        dest.writeString(attrName);
        dest.writeTypedList(values);
    }
}
