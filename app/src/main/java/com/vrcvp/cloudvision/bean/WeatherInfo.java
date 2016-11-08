package com.vrcvp.cloudvision.bean;

/**
 * 天气预报数据
 * Created by yinglovezhuzhu@gmail.com on 2016/11/8.
 */

public class WeatherInfo {
    private String city;
    private String cityid;
    private String temp1;
    private String temp2;
    private String weather;
    private String img1;
    private String img2;
    private String ptime;

    public String getCity() {
        return city;
    }

    public String getCityid() {
        return cityid;
    }

    public String getTemp1() {
        return temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public String getWeather() {
        return weather;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getPtime() {
        return ptime;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "city='" + city + '\'' +
                ", cityid='" + cityid + '\'' +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                ", weather='" + weather + '\'' +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", ptime='" + ptime + '\'' +
                '}';
    }
}
