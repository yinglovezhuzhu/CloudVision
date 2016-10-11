package com.xunda.cloudvision.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.resp.RecommendedProductResp;
import com.xunda.cloudvision.presenter.CorporatePresenter;
import com.xunda.cloudvision.ui.adapter.RecommendedProductPagerAdapter;
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

        mCorporatePresenter = new CorporatePresenter(this, this);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_corporate_culture:
                gotoCorporateIntro(CorporateIntroActivity.PAGE_CORPORATE_CULTURE);
                break;
            case R.id.btn_corporate_honor:
                gotoCorporateIntro(CorporateIntroActivity.PAGE_CORPORATE_HONOR);
                break;
            case R.id.btn_corporate_environment:
                gotoCorporateIntro(CorporateIntroActivity.PAGE_CORPORATE_ENVIRONMENT);
                break;
            case R.id.btn_corporate_image:
                gotoCorporateIntro(CorporateIntroActivity.PAGE_CORPORATE_IMAGE);
                break;
            case R.id.btn_corporate_intro:
                gotoCorporateIntro(CorporateIntroActivity.PAGE_CORPORATE_INTRO);
                break;
            case R.id.btn_corporate_all_product:
                startActivity(new Intent(this, ProductActivity.class));
                break;
            case R.id.btn_corporate_cloud_video:
                startActivity(new Intent(this, VideoActivity.class));
                break;
            case R.id.ibtn_corporate_back:
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
    public void onQueryRecommendedProductResult(RecommendedProductResp result) {

    }

    private void initView() {
        findViewById(R.id.btn_corporate_culture).setOnClickListener(this);
        findViewById(R.id.btn_corporate_honor).setOnClickListener(this);
        findViewById(R.id.btn_corporate_environment).setOnClickListener(this);
        findViewById(R.id.btn_corporate_image).setOnClickListener(this);
        findViewById(R.id.btn_corporate_intro).setOnClickListener(this);
        findViewById(R.id.btn_corporate_all_product).setOnClickListener(this);
        findViewById(R.id.btn_corporate_cloud_video).setOnClickListener(this);
        findViewById(R.id.ibtn_corporate_back).setOnClickListener(this);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_corporate_recommended_product);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.contentPadding_level2));
        viewPager.setAdapter(new RecommendedProductPagerAdapter(getSupportFragmentManager()));
    }

    /**
     * 跳转到企业简介页面
     * @param page 页面
     * @see CorporateIntroActivity#PAGE_CORPORATE_HONOR
     * @see CorporateIntroActivity#PAGE_CORPORATE_CULTURE
     * @see CorporateIntroActivity#PAGE_CORPORATE_IMAGE
     * @see CorporateIntroActivity#PAGE_CORPORATE_INTRO
     * @see CorporateIntroActivity#PAGE_CORPORATE_ENVIRONMENT
     */
    private void gotoCorporateIntro(int page) {
        Intent intent = new Intent(this, CorporateIntroActivity.class);
        intent.putExtra(Config.EXTRA_DATA, page);
        startActivity(intent);
    }
}
