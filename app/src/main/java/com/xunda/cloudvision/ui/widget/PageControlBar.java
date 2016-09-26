/*
 * 文件名：PageControlBar.java
 * 版权：<版权>
 * 描述：<描述>
 * 创建人：xiaoying
 * 创建时间：2013-5-31
 * 修改人：xiaoying
 * 修改时间：2013-5-31
 * 版本：v1.0
 */

package com.xunda.cloudvision.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xunda.cloudvision.R;

/**
 * 功能：页面显示控制条
 * @author xiaoying
 *
 */
public class PageControlBar extends LinearLayout {

	private Context mContext;
    private RadioGroup mRadioGroup;
    private int mCurrentPage = -1;

	public PageControlBar(Context context) {
		super(context);
		init(context);
	}

	public PageControlBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);

        mRadioGroup = new RadioGroup(context);
        mRadioGroup.setOrientation(HORIZONTAL);
        mRadioGroup.setGravity(Gravity.CENTER);

        addView(mRadioGroup);
	}

    private RadioButton newRadioButton(Context context) {
        RadioButton radioButton = new RadioButton(context);
        radioButton.setButtonDrawable(R.drawable.selector_page_indicator);
        radioButton.setPadding(5, 0, 5, 0);
        return radioButton;
    }

    /**
     * 设置页数
     * @param count
     */
	public void setPageCount(int count) {
        int currentCount = mRadioGroup.getChildCount();
        if(count > currentCount) {
            for(int i = 0; i < count - currentCount; i++) {
                mRadioGroup.addView(newRadioButton(mContext));
            }
        } else if(count < currentCount) {
            mRadioGroup.removeViews(count, currentCount - count);
        }
	}

    /**
     * 获取当前指示页面
     * @return
     */
    public int getCurrentPage() {
        return mCurrentPage;
    }

    /**
     * 设置当前页
     * @param currentItem
     */
	public void setCurrentPage(int currentItem) {
        if(mRadioGroup.getChildCount() == 0 || currentItem < 0) {
            return;
        }
        mCurrentPage = currentItem % mRadioGroup.getChildCount();
        RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(mCurrentPage);
        radioButton.setChecked(true);
	}

    /**
     * 指示器显示第一页
     */
    public void setFirstPage() {
        if(mRadioGroup.getChildCount() < 1) {
            return;
        }
        setCurrentPage(0);
    }

    /**
     * 指示器显示最后一页
     */
    public void setLastPage() {
        if(mRadioGroup.getChildCount() < 1) {
            return;
        }
        setCurrentPage(mRadioGroup.getChildCount() - 1);
    }

}
