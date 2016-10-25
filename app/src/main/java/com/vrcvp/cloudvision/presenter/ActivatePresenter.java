package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.ActivateResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.model.IActivateModel;
import com.vrcvp.cloudvision.model.ActivateModel;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.IActivateView;

/**
 * 激活Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class ActivatePresenter {

    private IActivateView mView;
    private IActivateModel mModel;

    public ActivatePresenter(Context context, IActivateView registerView) {
        this.mView = registerView;
        this.mModel = new ActivateModel(context);
    }

    public void activate() {
        final String code = mView.getCodeText();
        if(StringUtils.isEmpty(code)) {
            mView.onCodeEmpty();
            return;
        }
        mModel.activate(code, new HttpAsyncTask.Callback<ActivateResp>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onResult(ActivateResp result) {
                mView.onActivateResult(result);
            }
        });
    }
}
