package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.fragment.WebViewFragment;
import com.xunda.cloudvision.ui.widget.CompanyTabItem;
import com.xunda.cloudvision.view.ICorporateView;

/**
 * 企业首页
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class CorporateActivity extends BaseActivity implements ICorporateView {

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
        CompanyTabItem honorTab = new CompanyTabItem(this, R.string.str_corporate_honor);
        CompanyTabItem cultureTab = new CompanyTabItem(this, R.string.str_corporate_culture);
        CompanyTabItem imageTab = new CompanyTabItem(this, R.string.str_corporate_image);
        CompanyTabItem introTab = new CompanyTabItem(this, R.string.str_corporate_intro);
        CompanyTabItem environmentTab = new CompanyTabItem(this, R.string.str_corporate_environment);

        // 企业荣誉
        final Bundle honorArgs = new Bundle();
        honorArgs.putString(Config.EXTRA_DATA, "https://www.baidu.com");
        tabHost.addTab(tabHost.newTabSpec("honorTab").setIndicator(honorTab),
                WebViewFragment.class, honorArgs);

        // 企业文化
        final Bundle cultureArgs = new Bundle();
        cultureArgs.putString(Config.EXTRA_DATA, "https://www.so.com/");
        tabHost.addTab(tabHost.newTabSpec("cultureTab").setIndicator(cultureTab),
                WebViewFragment.class, cultureArgs);

        // 企业形象
        final Bundle imageArgs = new Bundle();
        imageArgs.putString(Config.EXTRA_DATA, "http://dict.youdao.com/");
        tabHost.addTab(tabHost.newTabSpec("imageTab").setIndicator(imageTab),
                WebViewFragment.class, imageArgs);

        // 企业简介
        final Bundle introArgs = new Bundle();
        introArgs.putString(Config.EXTRA_DATA, "http://www.liantu.com/");
        tabHost.addTab(tabHost.newTabSpec("introTab").setIndicator(introTab),
                WebViewFragment.class, introArgs);

        // 企业环境
        final Bundle environmentArgs = new Bundle();
        environmentArgs.putString(Config.EXTRA_DATA, "http://www.express8.cn/");
        tabHost.addTab(tabHost.newTabSpec("environmentTab").setIndicator(environmentTab),
                WebViewFragment.class, environmentArgs);
    }
}
