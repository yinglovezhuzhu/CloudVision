package com.vrcvp.cloudvision.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.resp.VoiceSearchResp;
import com.vrcvp.cloudvision.presenter.VoiceSearchPresenter;
import com.vrcvp.cloudvision.view.IVoiceSearchView;

/**
 * 声音搜索结果Activity，企业搜索结果
 * Created by yinglovezhuzhu@gmail.com on 2016/11/13.
 * @deprecated
 */

public class VoiceSearchActivity extends BaseActivity implements IVoiceSearchView {

    private VoiceSearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice_search);

        mSearchPresenter = new VoiceSearchPresenter(this, this);
    }

    @Override
    protected void onDestroy() {
        mSearchPresenter.cancelSearch();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_voice_search_back:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onVoiceSearchResult(VoiceSearchResp result) {

    }

    private void search(String keyword) {
        showLoadingDialog(null, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mSearchPresenter.cancelSearch();
            }
        });
        mSearchPresenter.search(keyword);
    }
}
