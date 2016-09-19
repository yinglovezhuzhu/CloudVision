package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.fragment.WebViewFragment;
import com.xunda.cloudvision.ui.widget.CompanyHomeTabItem;

/**
 * 企业首页
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class CorporateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_corporate);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_corporate_back:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    private void initView() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.fth_corporate_tabs);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_corporate_container);
        tabHost.getTabWidget().setDividerDrawable(null);

        addTabs(tabHost);

        findViewById(R.id.btn_corporate_back).setOnClickListener(this);
    }

    /**
     * 添加标签
     * @param tabHost
     */
    private void addTabs(FragmentTabHost tabHost) {
        CompanyHomeTabItem honorTab = new CompanyHomeTabItem(this, R.string.str_corporate_honor);
        CompanyHomeTabItem cultureTab = new CompanyHomeTabItem(this, R.string.str_corporate_culture);
        CompanyHomeTabItem imageTab = new CompanyHomeTabItem(this, R.string.str_corporate_image);
        CompanyHomeTabItem introTab = new CompanyHomeTabItem(this, R.string.str_corporate_intro);
        CompanyHomeTabItem environmentTab = new CompanyHomeTabItem(this, R.string.str_corporate_environment);

        tabHost.addTab(tabHost.newTabSpec("honorTab").setIndicator(honorTab), WebViewFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("cultureTab").setIndicator(cultureTab), WebViewFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("imageTab").setIndicator(imageTab), WebViewFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("introTab").setIndicator(introTab), WebViewFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("environmentTab").setIndicator(environmentTab), WebViewFragment.class, null);
    }
}
