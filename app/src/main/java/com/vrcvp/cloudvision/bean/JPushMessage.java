package com.vrcvp.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * JPush推送消息（通知和自定义消息）
 * Created by yinglovezhuzhu@gmail.com on 2016/11/11.
 */

public class JPushMessage implements Parcelable {
    private int id;
    private String title;
    private String message;
    private String alert;
    private String extra; // JSON字符串
    private String contentType;
    private String appKey;

    public JPushMessage() {

    }

    protected JPushMessage(Parcel in) {
        id = in.readInt();
        title = in.readString();
        message = in.readString();
        alert = in.readString();
        extra = in.readString();
        contentType = in.readString();
        appKey = in.readString();
    }

    public static final Creator<JPushMessage> CREATOR = new Creator<JPushMessage>() {
        @Override
        public JPushMessage createFromParcel(Parcel in) {
            return new JPushMessage(in);
        }

        @Override
        public JPushMessage[] newArray(int size) {
            return new JPushMessage[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "JPushMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", alert='" + alert + '\'' +
                ", extra='" + extra + '\'' +
                ", contentType='" + contentType + '\'' +
                ", appKey='" + appKey + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(extra);
        dest.writeString(alert);
        dest.writeString(contentType);
        dest.writeString(appKey);
    }
}
