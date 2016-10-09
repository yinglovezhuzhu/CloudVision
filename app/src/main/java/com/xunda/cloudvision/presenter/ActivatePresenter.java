package com.xunda.cloudvision.presenter;

import com.xunda.cloudvision.bean.resp.ActivateResp;
import com.xunda.cloudvision.http.HttpAsyncTask;
import com.xunda.cloudvision.model.IActivateModel;
import com.xunda.cloudvision.model.ActivateModel;
import com.xunda.cloudvision.utils.StringUtils;
import com.xunda.cloudvision.view.IActivateView;

/**
 * 激活Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class ActivatePresenter {

    private IActivateView mView;
    private IActivateModel mModel;

    public ActivatePresenter(IActivateView registerView) {
        this.mView = registerView;
        this.mModel = new ActivateModel();
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
