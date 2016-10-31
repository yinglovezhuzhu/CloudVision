package com.vrcvp.cloudvision.model;

import com.vrcvp.cloudvision.bean.resp.ActivateResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;

/**
 * 激活Model接口
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public interface IActivateModel extends IModel {

    void activate(String code, HttpAsyncTask.Callback<ActivateResp> callback);

    void cancelActivate();
}
