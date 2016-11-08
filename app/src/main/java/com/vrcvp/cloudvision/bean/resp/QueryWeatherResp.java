package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.WeatherInfo;

/**
 * 查询天气请求返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/8.
 */

public class QueryWeatherResp extends BaseResp<Object> {

    private WeatherInfo weatherinfo;

    public WeatherInfo getWeatherinfo() {
        return weatherinfo;
    }

    @Override
    public String toString() {
        return "QueryWeatherResp{" +
                "weatherinfo=" + weatherinfo +
                "} " + super.toString();
    }
}
