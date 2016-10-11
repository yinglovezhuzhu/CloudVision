package com.xunda.cloudvision.model;

import android.content.Context;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.bean.req.ActivateReq;
import com.xunda.cloudvision.bean.resp.ActivateResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.utils.SharedPrefHelper;
import com.xunda.cloudvision.utils.StringUtils;

/**
 * 激活页面Model
 * Created by yinglovezhuzhu@gmail.com on 2016/8/20.
 */
public class ActivateModel implements IActivateModel {

    private SharedPrefHelper mSharePrefHelper = null;

    public ActivateModel(Context context) {
        mSharePrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
    }

    @Override
    public boolean isActivated() {
        return !StringUtils.isEmpty(mSharePrefHelper.getString(Config.SP_KEY_ACTIVATE_CODE, null));
    }

    @Override
    public void activate(String code, final HttpAsyncTask.Callback<ActivateResp> callback) {

        ActivateReq reqParam = new ActivateReq();
        new HttpAsyncTask<ActivateResp>().execute("", reqParam, ActivateResp.class, new HttpAsyncTask.Callback<ActivateResp>() {
            @Override
            public void onPreExecute() {
                if(null != callback) {
                    callback.onPreExecute();
                }
            }

            @Override
            public void onCanceled() {
                if(null != callback) {
                    callback.onCanceled();
                }
            }

            @Override
            public void onResult(ActivateResp result) {
                if(null != callback) {
                    callback.onResult(result);
                }
            }
        });

    }
}
