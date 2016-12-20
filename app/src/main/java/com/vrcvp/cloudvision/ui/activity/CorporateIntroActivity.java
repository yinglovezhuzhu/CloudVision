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
//    /** 企业荣誉页面 **/
//    public static final int PAGE_CORPORATE_HONOR = 3;
//    /** 企业形象页面 **/
//    public static final int PAGE_CORPORATE_IMAGE = 4;
//    /** 企业简介页面 **/
//    public static final int PAGE_CORPORATE_INTRO = 5;
//    /** 企业环境页面 **/
//    public static final int PAGE_CORPORATE_ENVIRONMENT = 6;

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
//        CompanyTabItem honorTab = new CompanyTabItem(this, R.string.str_corporate_honor);
//        CompanyTabItem imageTab = new CompanyTabItem(this, R.string.str_corporate_image);
//        CompanyTabItem introTab = new CompanyTabItem(this, R.string.str_corporate_intro);
//        CompanyTabItem environmentTab = new CompanyTabItem(this, R.string.str_corporate_environment);

        // 企业文化
        final Bundle cultureArgs = new Bundle();
        if(null != mCorporateData) {
//            cultureArgs.putString(Config.EXTRA_DATA, mCorporateData.getCulture());
            cultureArgs.putString(Config.EXTRA_DATA,
                    StringUtils.formatHTMLContent(mCorporateData.getCulture(), getString(R.string.str_corporate_culture)));
        }
        tabHost.addTab(tabHost.newTabSpec("cultureTab").setIndicator(cultureTab),
                WebViewFragment.class, cultureArgs);

        // 企业新闻
        final Bundle newsArgs = new Bundle();
        if(null != mCorporateData) {
            // FIXME 修改显示内容
            newsArgs.putString(Config.EXTRA_DATA,
                    StringUtils.formatHTMLContent(mCorporateData.getHonor(), getString(R.string.str_corporate_news)));
        }
        tabHost.addTab(tabHost.newTabSpec("newsTab").setIndicator(newsTab),
                WebViewFragment.class, newsArgs);

        // 关于我们（内容为企业简介）
        final Bundle aboutUsArgs = new Bundle();
        if(null != mCorporateData) {
            aboutUsArgs.putString(Config.EXTRA_DATA,
                    StringUtils.formatHTMLContent(mCorporateData.getSummary(), getString(R.string.str_corporate_about_us)));
        }
        tabHost.addTab(tabHost.newTabSpec("aboutUsTab").setIndicator(aboutUsTab),
                WebViewFragment.class, aboutUsArgs);


//        // 企业荣誉
//        final Bundle honorArgs = new Bundle();
//        if(null != mCorporateData) {
////            honorArgs.putString(Config.EXTRA_DATA, mCorporateData.getHonor());
//            honorArgs.putString(Config.EXTRA_DATA,
//                    StringUtils.formatHTMLContent(mCorporateData.getHonor(), getString(R.string.str_corporate_honor)));
//        }
//        tabHost.addTab(tabHost.newTabSpec("honorTab").setIndicator(honorTab),
//                WebViewFragment.class, honorArgs);
//
//
//        // 企业形象
//        final Bundle imageArgs = new Bundle();
//        if(null != mCorporateData) {
////            imageArgs.putString(Config.EXTRA_DATA, mCorporateData.getFigure());
//            imageArgs.putString(Config.EXTRA_DATA,
//                    StringUtils.formatHTMLContent(mCorporateData.getFigure(), getString(R.string.str_corporate_image)));
//        }
//        tabHost.addTab(tabHost.newTabSpec("imageTab").setIndicator(imageTab),
//                WebViewFragment.class, imageArgs);
//
//        // 企业简介
//        final Bundle introArgs = new Bundle();
//        if(null != mCorporateData) {
//            introArgs.putString(Config.EXTRA_DATA,
//                    StringUtils.formatHTMLContent(mCorporateData.getSummary(), getString(R.string.str_corporate_intro)));
//        }
//        tabHost.addTab(tabHost.newTabSpec("introTab").setIndicator(introTab),
//                WebViewFragment.class, introArgs);
//
//        // 企业环境
//        final Bundle environmentArgs = new Bundle();
//        if(null != mCorporateData) {
//            environmentArgs.putString(Config.EXTRA_DATA,
//                    StringUtils.formatHTMLContent(mCorporateData.getEnvironment(), getString(R.string.str_corporate_environment)));
//        }
//        tabHost.addTab(tabHost.newTabSpec("environmentTab").setIndicator(environmentTab),
//                WebViewFragment.class, environmentArgs);
    }
}
