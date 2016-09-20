package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.presenter.CorporatePresenter;
import com.xunda.cloudvision.ui.fragment.WebViewFragment;
import com.xunda.cloudvision.ui.widget.CompanyTabItem;
import com.xunda.cloudvision.view.ICorporateView;

/**
 * 企业首页
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class CorporateActivity extends BaseActivity implements ICorporateView {

    private CorporatePresenter mCorporatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_corporate);

        mCorporatePresenter = new CorporatePresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private void initView() {
    }

}
