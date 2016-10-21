package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.resp.ActivateResp;
import com.xunda.cloudvision.http.HttpStatus;
import com.xunda.cloudvision.presenter.ActivatePresenter;
import com.xunda.cloudvision.view.IActivateView;

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
        if(HttpStatus.SC_OK == result.getHttpCode()) {
            finish(RESULT_OK, null);
        } else {
            showShortToast(result.getMsg());
        }
    }

    private void initView() {
        mEtCode = (EditText) findViewById(R.id.et_activate_code);
        findViewById(R.id.btn_activate_register).setOnClickListener(this);
    }

}
