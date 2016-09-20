package com.xunda.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 产品信息数据
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class ProductBean implements Parcelable {

    public ProductBean() {

    }

    protected ProductBean(Parcel in) {
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel in) {
            return new ProductBean(in);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
