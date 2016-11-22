package com.vrcvp.cloudvision.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * JPush消息Extra部分的数据
 * Created by yinglovezhuzhu@gmail.com on 2016/11/21.
 */

public class JPushExtra implements Parcelable {

    /** 类型：关机 **/
    public static final String TYPE_SHUTDOWN = "1";
    /** 类型：开机/重启 **/
    public static final String TYPE_REBOOT = "2";

    private String type;
    private String code;
    private String extrasMessage;

    protected JPushExtra(Parcel in) {
        type = in.readString();
        code = in.readString();
        extrasMessage = in.readString();
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

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getExtrasMessage() {
        return extrasMessage;
    }

    @Override
    public String toString() {
        return "JPushExtra{" +
                "type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", extrasMessage='" + extrasMessage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(code);
        dest.writeString(extrasMessage);
    }
}
