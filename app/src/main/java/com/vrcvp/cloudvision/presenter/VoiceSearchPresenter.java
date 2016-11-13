package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.model.IVoiceSearchModel;
import com.vrcvp.cloudvision.model.VoiceSearchModel;
import com.vrcvp.cloudvision.view.IVoiceSearchView;

/**
 * 语音搜索Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/11/13.
 */

public class VoiceSearchPresenter {

    private IVoiceSearchView mView;
    private IVoiceSearchModel mModel;

    public VoiceSearchPresenter(Context context, IVoiceSearchView view) {
        this.mView = view;
        this.mModel = new VoiceSearchModel(context);
    }


    public void search(String keyword) {
        mModel.searchVoiceRequest(keyword, 1, new HttpAsyncTask.Callback<VoiceSearchResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mView.onCanceled(null);
            }

            @Override
            public void onResult(VoiceSearchResp result) {
                mView.onVoiceSearchResult(result);
            }
        });
    }

    public void cancelSearch() {
        mModel.cancelSearchVoiceRequest();
    }
}
