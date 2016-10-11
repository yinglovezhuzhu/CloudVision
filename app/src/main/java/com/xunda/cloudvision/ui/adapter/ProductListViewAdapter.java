package com.xunda.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.ui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品列表浏览模式适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/22.
 */

public class ProductListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductBean> mData = new ArrayList<>();
    private int mWidth = 200;
    private int mHeight = 430;
    private int mNumColumns = 2;
    private int mHorizontalSpacing = 0;

    private OnProductItemClickListener mOnProductItemClickListener;

    public ProductListViewAdapter(Context context, int width, int height,
                                  int numColumns, int horizontalSpacing) {
        mContext = context;
        this.mWidth = width;
        this.mHeight = height;
        this.mNumColumns = numColumns;
        this.mHorizontalSpacing = horizontalSpacing;
    }

    /**
     * 设置点击监听
     * @param listener 点击监听
     */
    public void setOnProductItemClickListener(OnProductItemClickListener listener) {
        this.mOnProductItemClickListener = listener;
    }

    /**
     * 获取数据
     * @param row 行
     * @param column 列
     * @return 数据
     */
    public ProductBean getData(int row, int column) {
        final ProductBean [] rowData = getItem(row);
        if(column < 0 || column >= rowData.length) {
            return null;
        }
        return rowData[column];
    }

    @Override
    public int getCount() {
        // FIXME 真是数据使用注释代码
//        if(mData.size() % mNumColumns == 0) {
//            return mData.size() / mNumColumns;
//        } else {
//            return mData.size() / mNumColumns + 1;
//        }
        return 20;
    }

    @Override
    public ProductBean [] getItem(int i) {
        final ProductBean [] rowData = new ProductBean[mNumColumns];
        int index = i * mNumColumns;
        for(int j = 0; j < mNumColumns; j++) {
            if(index + j < mNumColumns) {
                rowData[j] = mData.get(index + j);
            } else {
                break;
            }
        }
        return rowData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final NoScrollGridView gridView;
        if(null == view) {
            gridView = (NoScrollGridView) View.inflate(mContext, R.layout.item_product_list_view, null);
            gridView.setNumColumns(mNumColumns);
            gridView.setHorizontalSpacing(mHorizontalSpacing);
            view = gridView;
        } else {
            gridView = (NoScrollGridView) view;
        }
        gridView.setAdapter(new ProductListViewItemAdapter(mContext, mWidth,
                mHeight, mNumColumns));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(null != mOnProductItemClickListener) {
                    mOnProductItemClickListener.onItemClicked(i, position);
                }
            }
        });
        return view;
    }

    public interface OnProductItemClickListener {
        void onItemClicked(int row, int column);
    }
}
