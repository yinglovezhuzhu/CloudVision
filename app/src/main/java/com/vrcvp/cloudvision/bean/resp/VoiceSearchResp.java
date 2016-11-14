package com.vrcvp.cloudvision.bean.resp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 语音搜索结果返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/11.
 */

public class VoiceSearchResp extends BaseResp<List<VoiceSearchResp.VoiceSearchData>> {

    public VoiceSearchResp() {
    }

    public VoiceSearchResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    public static class VoiceSearchData implements Parcelable {
        private int id;
        private int sex;
        private int action;
        private int type;
        private String content;
        private String url;
        private String outLink;
        private String robotName;

        protected VoiceSearchData(Parcel in) {
            id = in.readInt();
            sex = in.readInt();
            action = in.readInt();
            type = in.readInt();
            content = in.readString();
            url = in.readString();
            outLink = in.readString();
            robotName = in.readString();
        }

        public static final Creator<VoiceSearchData> CREATOR = new Creator<VoiceSearchData>() {
            @Override
            public VoiceSearchData createFromParcel(Parcel in) {
                return new VoiceSearchData(in);
            }

            @Override
            public VoiceSearchData[] newArray(int size) {
                return new VoiceSearchData[size];
            }
        };

        public int getId() {
            return id;
        }

        public int getSex() {
            return sex;
        }

        public int getAction() {
            return action;
        }

        public int getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }

        public String getOutLink() {
            return outLink;
        }

        public String getRobotName() {
            return robotName;
        }

        @Override
        public String toString() {
            return "VoiceSearchData{" +
                    "id=" + id +
                    ", sex=" + sex +
                    ", action=" + action +
                    ", type=" + type +
                    ", content='" + content + '\'' +
                    ", url='" + url + '\'' +
                    ", outLink='" + outLink + '\'' +
                    ", robotName='" + robotName + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(sex);
            dest.writeInt(action);
            dest.writeInt(type);
            dest.writeString(content);
            dest.writeString(url);
            dest.writeString(outLink);
            dest.writeString(robotName);
        }
    }
}
