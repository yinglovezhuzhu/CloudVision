package com.vrcvp.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品信息数据(产品列表（产品列表部分字段没有数据）， 产品详情)
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */
public class ProductBean implements Parcelable {

    private String id;	// 产品id
    private String categoryName;	// 产品类别名称
    private String name;	// 产品名称
    private String price;	// 价格
    private String orderWeight;	// 序号
    private String imageUrl;	// 图片url
    private List<String> detailImages = new ArrayList<>(); // 细节图
    private String detail; // 产品详情
    private List<AttrBean> attrValues = new ArrayList<>(); // 属性名和属性值列表

    public ProductBean() {

    }

    protected ProductBean(Parcel in) {
        id = in.readString();
        categoryName = in.readString();
        name = in.readString();
        price = in.readString();
        orderWeight = in.readString();
        imageUrl = in.readString();
        detailImages = in.createStringArrayList();
        detail = in.readString();
        attrValues = in.createTypedArrayList(AttrBean.CREATOR);
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(categoryName);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(orderWeight);
        dest.writeString(imageUrl);
        dest.writeStringList(detailImages);
        dest.writeString(detail);
        dest.writeTypedList(attrValues);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderWeight() {
        return orderWeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getDetailImages() {
        return detailImages;
    }

    public String getDetail() {
        return detail;
    }

    public List<AttrBean> getAttrValues() {
        return attrValues;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", orderWeight='" + orderWeight + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", detailImages=" + detailImages +
                ", detail='" + detail + '\'' +
                ", attrValues=" + attrValues +
                '}';
    }
}
