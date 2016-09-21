package com.xunda.cloudvision.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.adapter.ProductPagerViewAdapter;

/**
 * 产品浏览模式Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductPagerViewFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TextView mTvPageNum;
    private TextView mTvPageCount;
    private ProductPagerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_pager_view, container, false);

        initView(contentView);

        return contentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_product_pager_view_pre_page:
                prePage();
                break;
            case R.id.ibtn_product_pager_view_next_page:
                nextPage();
                break;
            default:
                break;
        }
    }

    private void initView(View contentView) {
        mViewPager = (ViewPager) contentView.findViewById(R.id.vp_product_pager_view);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.contentPadding_level0));
        mAdapter = new ProductPagerViewAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTvPageNum = (TextView) contentView.findViewById(R.id.tv_product_pager_view_page_num);
        mTvPageCount = (TextView) contentView.findViewById(R.id.tv_product_pager_view_page_count);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvPageNum.setText(String.valueOf(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvPageNum.setText(String.valueOf(mViewPager.getCurrentItem() + 1));
        mTvPageCount.setText(String.valueOf(mAdapter.getCount()));

        contentView.findViewById(R.id.ibtn_product_pager_view_pre_page).setOnClickListener(this);
        contentView.findViewById(R.id.ibtn_product_pager_view_next_page).setOnClickListener(this);
    }

    /**
     * 上一页
     */
    private void prePage() {
        if(mAdapter.getCount() < 1 || mViewPager.getCurrentItem() == 0) {
            return;
        }
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1 , true);
    }

    /**
     * 下一页
     */
    private void nextPage() {
        if(mAdapter.getCount() < 1 || mViewPager.getCurrentItem() == mAdapter.getCount() - 1) {
            return;
        }
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1 , true);
    }
}
