package com.vrcvp.cloudvision.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.CorporateBean;
import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.bean.VideoBean;
import com.vrcvp.cloudvision.bean.resp.QueryCorporateResp;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.bean.resp.QueryVideoResp;
import com.vrcvp.cloudvision.http.HttpStatus;
import com.vrcvp.cloudvision.presenter.CorporatePresenter;
import com.vrcvp.cloudvision.ui.adapter.RecommendedProductPagerAdapter;
import com.vrcvp.cloudvision.ui.adapter.RecommendedVideoAdapter;
import com.vrcvp.cloudvision.ui.widget.NoScrollGridView;
import com.vrcvp.cloudvision.utils.DataManager;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.view.ICorporateView;

import java.util.List;

/**
 * 企业首页
 * Created by yinglovezhuzhu@gmail.com on 2016/9/18.
 */
public class CorporateActivity extends BaseActivity implements ICorporateView {

    private CorporatePresenter mCorporatePresenter;

    private ImageView mIvCorporateLogo;
    private TextView mTvCorporateName;

    private RecommendedProductPagerAdapter mRecommendedProductAdapter;
    private RecommendedVideoAdapter mRecommendedVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_corporate);

        mCorporatePresenter = new CorporatePresenter(this, this);

        initView();

        mCorporatePresenter.queryCorporateInfo();
        mCorporatePresenter.queryRecommendedProduct();
        mCorporatePresenter.queryRecommendVideo();
    }

    @Override
    protected void onDestroy() {
        mCorporatePresenter.onDestory();
        super.onDestroy();
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
            case R.id.btn_corporate_all_video:
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
    public void onQueryCorporateInfoResult(QueryCorporateResp result) {
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    final CorporateBean corporate = result.getData();
                    if(null == corporate) {
                        // TODO 错误
                    } else {
                        DataManager.getInstance().updateCorporateInfo(corporate);
                        mTvCorporateName.setText(corporate.getName());
                        final String logo = corporate.getLogo();
                        if(!StringUtils.isEmpty(logo)) {
                            loadImage(logo, mIvCorporateLogo, R.drawable.ic_launcher, R.drawable.ic_launcher);
                        }
                    }
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // TODO 无网络，读取缓存错误
                    break;
                default:
                    // TODO 错误
                    break;
            }
        }
    }

    @Override
    public void onQueryRecommendedProductResult(QueryProductResp result) {
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<ProductBean> product = result.getData();
                    if(null == product || product.isEmpty()) {
                        // TODO 错误
                    } else {
                        mRecommendedProductAdapter.addAll(product, true);
                    }
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // TODO 无网络，读取缓存错误
                    break;
                default:
                    // TODO 错误
                    break;
            }
        }
    }

    @Override
    public void onQueryRecommendedVideoResult(QueryVideoResp result) {
        if(null == result) {

        } else {
            switch (result.getHttpCode()) {
                case HttpStatus.SC_OK:
                    List<VideoBean> video = result.getData();
                    if(null == video || video.isEmpty()) {
                        // TODO 错误
                    } else {
                        mRecommendedVideoAdapter.addAll(video, true);
                    }
                    break;
                case HttpStatus.SC_CACHE_NOT_FOUND:
                    // TODO 无网络，读取缓存错误
                    break;
                default:
                    // TODO 错误
                    break;
            }
        }
    }

    private void initView() {
        mIvCorporateLogo = (ImageView) findViewById(R.id.iv_corporate_logo);
        mTvCorporateName = (TextView) findViewById(R.id.tv_corporate_name);

        findViewById(R.id.btn_corporate_culture).setOnClickListener(this);
        findViewById(R.id.btn_corporate_honor).setOnClickListener(this);
        findViewById(R.id.btn_corporate_environment).setOnClickListener(this);
        findViewById(R.id.btn_corporate_image).setOnClickListener(this);
        findViewById(R.id.btn_corporate_intro).setOnClickListener(this);
        findViewById(R.id.btn_corporate_all_product).setOnClickListener(this);
        findViewById(R.id.ibtn_corporate_back).setOnClickListener(this);
        findViewById(R.id.btn_corporate_all_video).setOnClickListener(this);

        // 推荐产品ViewPager高度设置
        final int dmWidth = getResources().getDisplayMetrics().widthPixels;
        final View recommendedProductContent = findViewById(R.id.ll_corporate_recommended_product_content);
        ViewGroup.LayoutParams lp = recommendedProductContent.getLayoutParams();
        if(null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        lp.height = dmWidth * 4 / 5;
        recommendedProductContent.setLayoutParams(lp);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_corporate_recommended_product);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.contentPadding_level6));
        mRecommendedProductAdapter = new RecommendedProductPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mRecommendedProductAdapter);

        final int videoItemWidth = (dmWidth - getResources().getDimensionPixelSize(R.dimen.contentPadding_level4) * 3) / 2;
        final NoScrollGridView videoGrid = (NoScrollGridView) findViewById(R.id.gv_corporate_recommended_video);
        mRecommendedVideoAdapter = new RecommendedVideoAdapter(this, videoItemWidth);
        videoGrid.setAdapter(mRecommendedVideoAdapter);
        videoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final VideoBean video = mRecommendedVideoAdapter.getItem(position);
                if(null == video) {
                    return;
                }
                Intent i = new Intent(CorporateActivity.this, VideoPlayerActivity.class);
                // FIXME 改为视频地址
                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
                startActivity(i);
            }
        });

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
        intent.putExtra(Config.EXTRA_DATA, DataManager.getInstance().getCorporateInfo());
        intent.putExtra(Config.EXTRA_POSITION, page);
        startActivity(intent);
    }
}
