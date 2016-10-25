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
    private List<ImageBean> detailImages = new ArrayList<>(); // 细节图
    private String detail; // 产品详情
    private List<SkuBean> skus = new ArrayList<>(); // 产品sku

    public ProductBean() {

    }

    protected ProductBean(Parcel in) {
        id = in.readString();
        categoryName = in.readString();
        name = in.readString();
        price = in.readString();
        orderWeight = in.readString();
        imageUrl = in.readString();
        detailImages = in.createTypedArrayList(ImageBean.CREATOR);
        detail = in.readString();
        skus = in.createTypedArrayList(SkuBean.CREATOR);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(String orderWeight) {
        this.orderWeight = orderWeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ImageBean> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<ImageBean> detailImages) {
        this.detailImages = detailImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<SkuBean> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuBean> skus) {
        this.skus = skus;
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
                ", skus=" + skus +
                '}';
    }

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
        dest.writeTypedList(detailImages);
        dest.writeString(detail);
        dest.writeTypedList(skus);
    }
}
