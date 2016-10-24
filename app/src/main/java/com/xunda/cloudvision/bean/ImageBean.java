package com.xunda.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class ImageBean implements Parcelable {

    private String imgUrl;

    public ImageBean() {
String a = "";
    }

    protected ImageBean(Parcel in) {
        imgUrl = in.readString();
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "imgUrl='" + imgUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
    }
}
