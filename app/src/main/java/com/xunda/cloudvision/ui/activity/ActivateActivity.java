package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xunda.cloudvision.R;
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

        mActivatePresenter = new ActivatePresenter(this);
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

    }

    private void initView() {
        mEtCode = (EditText) findViewById(R.id.et_activate_code);
        findViewById(R.id.btn_activate_register).setOnClickListener(this);
    }
}
