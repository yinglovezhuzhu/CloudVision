package com.vrcvp.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.resp.ActivateResp;
import com.vrcvp.cloudvision.db.WeatherDBHelper;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.presenter.ActivatePresenter;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.Utils;
import com.vrcvp.cloudvision.view.IActivateView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 激活页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/13.
 */
public class ActivateActivity extends BaseActivity implements IActivateView {

    private EditText mEtCode;
    private ActivatePresenter mActivatePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activate);

        initView();

        mActivatePresenter = new ActivatePresenter(this, this);
    }

    @Override
    public void onBackPressed() {
        finish(RESULT_CANCELED, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_activate_register:
                mActivatePresenter.activate();
                break;
            default:
                break;
        }
    }

    @Override
    public String getCodeText() {
        return mEtCode.getText().toString();
    }

    @Override
    public void onCodeEmpty() {
        showShortToast(R.string.str_please_input_activation_code);
    }

    @Override
    public void onPreExecute(String key) {

    }

    @Override
    public void onCanceled(String key) {

    }

    @Override
    public void onActivateResult(ActivateResp result) {
        switch (result.getHttpCode()) {
            case HttpStatus.SC_OK:
                ActivateResp.ActivateRespData data = result.getData();
                if(null != data) {
                    Date date = Utils.parseTime(data.getEndTime(), Config.DATE_FORMAT_YMD);
                    if(null != date && result.getTimestamp() > date.getTime()) {
                        showShortToast(R.string.str_invalid_activate_code);
                        return;
                    }
                }

                finish(RESULT_OK, null);
                break;
            case HttpStatus.SC_BAD_REQUEST:
                showShortToast(R.string.str_invalid_activate_code);
                break;
            default:
                showShortToast(result.getMsg());
                break;
        }
    }

    private void initView() {
        mEtCode = (EditText) findViewById(R.id.et_activate_code);
        findViewById(R.id.btn_activate_register).setOnClickListener(this);
    }

}
