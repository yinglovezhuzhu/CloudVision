package com.vrcvp.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.CorporateBean;
import com.vrcvp.cloudvision.presenter.CorporateIntroPresenter;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.ICorporateIntroView;
import com.vrcvp.cloudvision.ui.fragment.WebViewFragment;
import com.vrcvp.cloudvision.ui.widget.CompanyTabItem;

/**
 * 企业详情页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/20.
 */

public class CorporateIntroActivity extends BaseActivity implements ICorporateIntroView {

    /** 企业文化页面 **/
    public static final int PAGE_CORPORATE_CULTURE = 0;
    /** 企业新闻页面 **/
    public static final int PAGE_CORPORATE_NEWS = 1;
    /** 关于我们页面 **/
    public static final int PAGE_CORPORATE_ABOUT_US = 2;

    private CorporateIntroPresenter mCorporateIntroPresenter;
    private CorporateBean mCorporateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_corporate_intro);

        mCorporateIntroPresenter = new CorporateIntroPresenter(this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_corporate_intro_back:
                finish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish(RESULT_CANCELED, null);
    }

    private void initView() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.fth_corporate_intro_tabs);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_corporate_intro_container);
        tabHost.getTabWidget().setDividerDrawable(null);
        findViewById(R.id.btn_corporate_intro_back).setOnClickListener(this);

        Intent intent = getIntent();
        if(null == intent) {
            finish(RESULT_CANCELED, null);
            return;
        }

        mCorporateData = intent.getParcelableExtra(Config.EXTRA_DATA);

        addTabs(tabHost);

        if(intent.hasExtra(Config.EXTRA_POSITION)) {
            int page = intent.getIntExtra(Config.EXTRA_POSITION, PAGE_CORPORATE_CULTURE);
            tabHost.setCurrentTab(page);
        }
    }

    /**
     * 添加标签
     * @param tabHost
     */
    private void addTabs(FragmentTabHost tabHost) {
        CompanyTabItem cultureTab = new CompanyTabItem(this, R.string.str_corporate_culture);
        CompanyTabItem newsTab = new CompanyTabItem(this, R.string.str_corporate_news);
        CompanyTabItem aboutUsTab = new CompanyTabItem(this, R.string.str_corporate_about_us);

        // 企业文化
        final Bundle cultureArgs = new Bundle();
        if(null != mCorporateData) {
            cultureArgs.putString(Config.EXTRA_DATA,
                    StringUtils.formatHTMLContent(mCorporateData.getCulture(), getString(R.string.str_corporate_culture)));
        }
        tabHost.addTab(tabHost.newTabSpec("cultureTab").setIndicator(cultureTab),
                WebViewFragment.class, cultureArgs);

        // 企业新闻
        final Bundle newsArgs = new Bundle();
        if(null != mCorporateData) {
            newsArgs.putString(Config.EXTRA_DATA,
                    StringUtils.formatHTMLContent(mCorporateData.getNews(), getString(R.string.str_corporate_news)));
        }
        tabHost.addTab(tabHost.newTabSpec("newsTab").setIndicator(newsTab),
                WebViewFragment.class, newsArgs);

        // 关于我们
        final Bundle aboutUsArgs = new Bundle();
        if(null != mCorporateData) {
            aboutUsArgs.putString(Config.EXTRA_DATA,
                    StringUtils.formatHTMLContent(mCorporateData.getAbout(), getString(R.string.str_corporate_about_us)));
        }
        tabHost.addTab(tabHost.newTabSpec("aboutUsTab").setIndicator(aboutUsTab),
                WebViewFragment.class, aboutUsArgs);
    }
}
