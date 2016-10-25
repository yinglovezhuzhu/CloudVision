package com.vrcvp.cloudvision.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensource.transformer.VerticalStackTransformer;
import com.opensource.view.OrientedViewPager;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.resp.QueryProductResp;
import com.vrcvp.cloudvision.observer.ProductObserver;
import com.vrcvp.cloudvision.ui.activity.ProductActivity;
import com.vrcvp.cloudvision.ui.adapter.ProductPagerViewAdapter;

/**
 * 产品浏览模式Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductPagerViewFragment extends BaseFragment {

//    private ViewPager mViewPager;
    private OrientedViewPager mViewPager;
    private TextView mTvPageNum;
    private TextView mTvPageCount;
    private ProductPagerViewAdapter mAdapter;


    private ProductObserver mProductObserver = new ProductObserver() {
        @Override
        public void onQueryProductResult(boolean isRefresh, boolean hasMore, QueryProductResp result) {
//            if(null != mLvProduct) {
//                mLvProduct.refreshCompleted();
//                // FIXME 是否可以加载更多根据加载分页结果决定
//                mLvProduct.loadMoreCompleted(true);
//            }
//            if(isRefresh) {
//                mAdapter.clear(true);
//            }
            mAdapter.addAll(result.getProduct(), true);
            if(null != mTvPageCount) {
                mTvPageCount.setText(String.valueOf(mAdapter.getCount()));
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductPagerViewAdapter(getChildFragmentManager());

        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            mAdapter.addAll(((ProductActivity) activity).getProductData(), true);
            ((ProductActivity) activity).registerProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_pager_view, container, false);

        initView(contentView);

        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewPager = null;
        mTvPageCount = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            ((ProductActivity) activity).unregisterProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }
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
        mViewPager = (OrientedViewPager) contentView.findViewById(R.id.vp_product_pager_view);
        mViewPager.setOrientation(OrientedViewPager.Orientation.VERTICAL);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setPageTransformer(true, new VerticalStackTransformer(getActivity()));
        mViewPager.setAdapter(mAdapter);

        mTvPageNum = (TextView) contentView.findViewById(R.id.tv_product_pager_view_page_num);
        mTvPageCount = (TextView) contentView.findViewById(R.id.tv_product_pager_view_page_count);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
