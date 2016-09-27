package com.xunda.cloudvision.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.adapter.ProductDetailImgAdapter;
import com.xunda.cloudvision.ui.widget.PageControlBar;
import com.xunda.cloudvision.view.IProductDetailView;

/**
 * 产品详情页面
 * Created by yinglovezhuzhu@gmail.com on 2016/9/26.
 */

public class ProductDetailActivity extends BaseActivity implements IProductDetailView {

    private PageControlBar mPageIndicator;
    private ProductDetailImgAdapter mImgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_product_detail_back:
                finish(RESULT_CANCELED, null);
                break;
            case R.id.ibtn_product_720_view_img:
                // 720度看图
                break;
            default:
                break;
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        findViewById(R.id.ibtn_product_detail_back).setOnClickListener(this);
        findViewById(R.id.ibtn_product_720_view_img).setOnClickListener(this);

        int dmWidth = getResources().getDisplayMetrics().widthPixels;
        int pageMargin = getResources().getDimensionPixelSize(R.dimen.product_detail_img_page_margin);
        ViewPager imgPager = (ViewPager) findViewById(R.id.vp_product_detail_img);
        ViewGroup.LayoutParams lp = imgPager.getLayoutParams();
        if(null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
        }
        lp.height = dmWidth * 2 / 3;
        imgPager.setLayoutParams(lp);
        // 调整缩放后的页面间距
        imgPager.setPageMargin(pageMargin);
        imgPager.setOffscreenPageLimit(3);
        mImgAdapter = new ProductDetailImgAdapter(getSupportFragmentManager());
        imgPager.setAdapter(mImgAdapter);

        mPageIndicator = (PageControlBar) findViewById(R.id.pb_product_detail_page_indicator);
        mPageIndicator.setPageCount(mImgAdapter.getCount());
        mPageIndicator.setCurrentPage(imgPager.getCurrentItem());

        imgPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPageIndicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imgPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            /** 最小缩放比例 **/
            private static final float PAGE_MIN_SCALE = 0.85f;
            /** 最小透明比例 **/
            private static final float PAGE_MIN_ALPHA = 0.85f;

            @Override
            public void transformPage(View page, float position) {
                if (position < -1) { // [-Infinity,-1)
                    page.setAlpha(PAGE_MIN_ALPHA);
                    page.setScaleX(PAGE_MIN_SCALE);
                    page.setScaleY(PAGE_MIN_SCALE);
                } else if (position <= 1) { // [-1,1]
                    float scaleFactor = Math.max(PAGE_MIN_SCALE, 1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
//                    page.setAlpha(PAGE_MIN_ALPHA + (scaleFactor - PAGE_MIN_SCALE) / (1 - PAGE_MIN_SCALE) * (1 - PAGE_MIN_ALPHA));
                } else {
//                    page.setAlpha(PAGE_MIN_ALPHA);
                    page.setScaleX(PAGE_MIN_SCALE);
                    page.setScaleY(PAGE_MIN_SCALE);
                }
            }
        });
    }
}
