package com.xunda.cloudvision.model;

import com.xunda.cloudvision.bean.resp.ActivateResp;
import com.xunda.cloudvision.http.HttpAsyncTask;

/**
 * 激活页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class ActivateModel implements IActivateModel {


    @Override
    public void activate(String code, HttpAsyncTask.Callback<ActivateResp> callback) {
        if(null != callback) {
            callback.onResult(null);
        }
    }
}
