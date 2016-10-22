package com.xunda.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 企业信息数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class CorporateBean implements Parcelable{
    private String id; // 企业id
    private String industryName; // 行业名称
    private String name; // 企业名称
    private String summary; // 企业简介
    private String culture; // 企业文化
    private String honor; // 企业荣誉
    private String environment; // 企业环境
    private String figure; // 企业形象
    private String contacts; // 联系人
    private String telnumber; // 联系电话
    private String address; // 地址

    public CorporateBean() {}

    protected CorporateBean(Parcel in) {
        id = in.readString();
        industryName = in.readString();
        name = in.readString();
        summary = in.readString();
        culture = in.readString();
        honor = in.readString();
        environment = in.readString();
        figure = in.readString();
        contacts = in.readString();
        telnumber = in.readString();
        address = in.readString();
    }

    public static final Creator<CorporateBean> CREATOR = new Creator<CorporateBean>() {
        @Override
        public CorporateBean createFromParcel(Parcel in) {
            return new CorporateBean(in);
        }

        @Override
        public CorporateBean[] newArray(int size) {
            return new CorporateBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(industryName);
        dest.writeString(name);
        dest.writeString(summary);
        dest.writeString(culture);
        dest.writeString(honor);
        dest.writeString(environment);
        dest.writeString(figure);
        dest.writeString(contacts);
        dest.writeString(telnumber);
        dest.writeString(address);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CorporateBean{" +
                "id='" + id + '\'' +
                ", industryName='" + industryName + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", culture='" + culture + '\'' +
                ", honor='" + honor + '\'' +
                ", environment='" + environment + '\'' +
                ", figure='" + figure + '\'' +
                ", contacts='" + contacts + '\'' +
                ", telnumber='" + telnumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
