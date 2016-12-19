package com.vrcvp.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * JPush消息Extra部分的数据
 * Created by yinglovezhuzhu@gmail.com on 2016/11/21.
 */

public class JPushExtra implements Parcelable {

    /** 类型：关机 **/
    public static final String TYPE_CLOSE_LCD_BACKLIGHT = "1";
    /** 类型：开机/重启 **/
    public static final String TYPE_OPEN_LCD_BACKLIGHT = "2";

    private String messageType;
    private String extraCode;
    private String mac;
    private String title;
    private String startTime;
    private String closeTime;
    private String currentTime;

    protected JPushExtra(Parcel in) {
        messageType = in.readString();
        extraCode = in.readString();
        mac = in.readString();
        title = in.readString();
        startTime = in.readString();
        closeTime = in.readString();
        currentTime = in.readString();
    }

    public static final Creator<JPushExtra> CREATOR = new Creator<JPushExtra>() {
        @Override
        public JPushExtra createFromParcel(Parcel in) {
            return new JPushExtra(in);
        }

        @Override
        public JPushExtra[] newArray(int size) {
            return new JPushExtra[size];
        }
    };

    public String getMessageType() {
        return messageType;
    }

    public String getExtraCode() {
        return extraCode;
    }

    public String getMac() {
        return mac;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageType);
        dest.writeString(extraCode);
        dest.writeString(mac);
        dest.writeString(title);
        dest.writeString(startTime);
        dest.writeString(closeTime);
        dest.writeString(currentTime);
    }
}
