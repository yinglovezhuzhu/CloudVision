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
    private String logo;
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
        logo = in.readString();
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
        dest.writeString(logo);
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

    public String getIndustryName() {
        return industryName;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getSummary() {
        return summary;
    }

    public String getCulture() {
        return culture;
    }

    public String getHonor() {
        return honor;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getFigure() {
        return figure;
    }

    public String getContacts() {
        return contacts;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CorporateBean{" +
                "id='" + id + '\'' +
                ", industryName='" + industryName + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
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
