package com.vrcvp.cloudvision.bean;

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
    private String address; // 地址
    private String contacts; // 联系人
    private String telnumber; // 联系电话
    private String about;
    private String culture; // 企业文化
    private String news;

    public CorporateBean() {}

    protected CorporateBean(Parcel in) {
        id = in.readString();
        industryName = in.readString();
        name = in.readString();
        logo = in.readString();
        address = in.readString();
        contacts = in.readString();
        telnumber = in.readString();
        about = in.readString();
        culture = in.readString();
        news = in.readString();
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

    public String getAddress() {
        return address;
    }

    public String getContacts() {
        return contacts;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public String getAbout() {
        return about;
    }

    public String getCulture() {
        return culture;
    }

    public String getNews() {
        return news;
    }

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
        dest.writeString(address);
        dest.writeString(contacts);
        dest.writeString(telnumber);
        dest.writeString(about);
        dest.writeString(culture);
        dest.writeString(news);
    }
}
