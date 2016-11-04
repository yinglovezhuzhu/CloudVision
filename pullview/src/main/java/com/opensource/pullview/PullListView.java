/*
 * Copyright (C)2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.opensource.pullview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.opensource.pullview.utils.DateUtil;

/**
 * Usage A Custom ListView can be pull to refresh and load more<br>
 * <p>Off by default pull-to-refresh and load-more, but turn them on when<br>
 * call {@link #setOnRefreshListener(com.opensource.pullview.OnRefreshListener)} and {@link #setOnLoadMoreListener(OnLoadMoreListener)}<br><br>
 * <p/>
 * <p>Pull-to-refresh and load-more can not doing at the same time.<br>
 * If pull-to-refresh is happening, you can't do load-more action befor pull-to refresh is finished.<br><br>
 * <p/>
 * <p>You need to call {@link #refreshCompleted()} when refresh thread finished,<br>
 * Similarly, You also need to call {@link #loadMoreCompleted(boolean)} when load thread finished.<br>
 *
 * @author yinglovezhuzhu@gmail.com
 */
public class PullListView extends BasePullListView {


    private PullHeaderView mHeaderView;

    private String mLastRefreshTime = "";
    private int mHeaderLabelVisibility = View.VISIBLE;

    /**
     * Constructor
     *
     * @param context
     */
    public PullListView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * Constructor
     *
     * @param context
     */
    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * Constructor
     *
     * @param context
     */
    public PullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    private int mStartY = 0;
    private boolean mRecording = false;
    private boolean mIsBack = false;
    

    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = (int) event.getY();
                if (!mRecording) {
                    mRecording = mVerticalScrollRange == mVerticalScrollOffset + mVerticalScrollExtent;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                int moveY = tempY - mStartY;
                if (mVerticalScrollOffset <= 0 || mFirstItemIndex == 0) {
                    if (!mRecording) {
                        mRecording = true;
                        mStartY = tempY;
                    }
                    int scrollY = moveY / OFFSET_RATIO;

                    if (mState != LOADING && (mEnablePullRefresh || mEnableOverScroll)) {
                        // Ensure that the process of setting padding, current position has always been at the header,
                        // or if when the list exceeds the screen, then, when the push, the list will scroll at the same time
                        switch (mState) {
                            case RELEASE_TO_LOAD: // Release to load data
                            	if(mFirstItemIndex + mVisibleItemCount < mTotalItemCount) {
                            		setSelection(mFirstItemIndex);
                            	}
                                // Slide up, header part was covered, but not all be covered(Pull up to cancel)
                                if (moveY > 0 && (scrollY < mHeaderView.mViewHeight)) {
                                    mState = PULL_TO_LOAD;
                                } else if (moveY <= 0 && mFirstItemIndex == 0) {
                                    // Slide to the top
                                    mState = IDEL;
                                }
                                updateHeaderViewByState(scrollY - mHeaderView.mViewHeight);
                                break;
                            case PULL_TO_LOAD:
                            	if(mFirstItemIndex + mVisibleItemCount < mTotalItemCount) {
                            		setSelection(mFirstItemIndex);
                            	}
                                // Pull down to the state can enter RELEASE_TO_REFRESH
                                if (scrollY >= mHeaderView.mViewHeight) {
                                    mState = RELEASE_TO_LOAD;
                                    mIsBack = true;
                                } else if (moveY <= 0 && mFirstItemIndex == 0) {
                                    mState = IDEL;
                                }
                                updateHeaderViewByState(scrollY - mHeaderView.mViewHeight);
                                break;
                            case IDEL:
                                if (moveY > 0 && mFirstItemIndex == 0) {
                                    mState = PULL_TO_LOAD;
                                }
                                updateHeaderViewByState(-mHeaderView.mViewHeight);
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mVerticalScrollOffset <= 0 || mFirstItemIndex == 0) {
                    switch (mState) {
                        case IDEL:
                            //Do nothing.
                            break;
                        case PULL_TO_LOAD:
                            //Pull to refresh.
                            mState = IDEL;
                            updateHeaderViewByState(-mHeaderView.mViewHeight);
                            break;
                        case RELEASE_TO_LOAD:
                            if (mEnablePullRefresh) {
                                //Release to refresh.
                                updateHeaderViewByState(0);
                                refresh();
                                updateHeaderViewByState(0);
                            } else {
                                mState = IDEL;
                                updateHeaderViewByState(-mHeaderView.mViewHeight);
                            }
                            break;
                        default:
                            break;
                    }
                } 
                mRecording = false;
                mIsBack = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void refreshCompleted() {
        super.refreshCompleted();
        mRecording = false;
        mIsBack = false;
        mLastRefreshTime = DateUtil.getSystemDate(getResources().getString(R.string.pull_view_date_format));
        updateHeaderViewByState(-mHeaderView.mViewHeight);
    }

    /**
     * Show loading view on header<br>
     * <br><p>Use this method when no header view was added on PullListView.
     *
     * @param text
     */
    public void onHeadLoading(CharSequence text) {
        mState = LOADING;
        mHeaderView.setPadding(0, 0, 0, 0);
        mHeaderView.setArrowVisibility(View.GONE);
        mHeaderView.setProgressVisibility(View.VISIBLE);
        mHeaderView.setTitleVisibility(View.VISIBLE);
        mHeaderView.setLabelVisibility(View.GONE);
        mHeaderView.startArrowAnimation(null);
        mHeaderView.setTitleText(text);
        mHeaderView.setVisibility(View.VISIBLE);
    }

    /**
     * Show loading view on head<br>
     * <br><p>Use this method when no header view was added on PullListView.
     *
     * @param resId
     */
    public void onHeadLoading(int resId) {
        mState = LOADING;
        mHeaderView.setPadding(0, 0, 0, 0);
        mHeaderView.setArrowVisibility(View.GONE);
        mHeaderView.setProgressVisibility(View.VISIBLE);
        mHeaderView.setTitleVisibility(View.VISIBLE);
        mHeaderView.setLabelVisibility(View.GONE);
        mHeaderView.startArrowAnimation(null);
        mHeaderView.setTitleText(resId);
        mHeaderView.setVisibility(View.VISIBLE);
    }

    /**
     * Set last refresh time<br>
     * <p>The value of {@link #mLastRefreshTime} initialized to the time when create {@link com.opensource.pullview.PullListView} object.<br>
     * You can set this value.
     *
     * @param time
     */
    public void setLastRefreshTime(String time) {
        this.mLastRefreshTime = time;
    }

    /**
     * Set header view label's visibility.<br>
     * <p>You can set the value of {@link android.view.View#GONE}、{@link android.view.View#VISIBLE}<br>
     *
     * @param visibility
     * @see android.view.View#GONE
     * @see android.view.View#VISIBLE
     */
    public void setHeaderLabelVisibility(int visibility) {
        this.mHeaderLabelVisibility = visibility;
        if (mHeaderLabelVisibility == View.INVISIBLE) {
            mHeaderLabelVisibility = View.GONE;
            return;
        }
        mHeaderView.setLabelVisibility(mHeaderLabelVisibility);
    }

    /**
     * Init views
     *
     * @param context
     */
    private void initView(Context context) {

        mHeaderView = new PullHeaderView(context);
        mHeaderView.setLabelVisibility(View.VISIBLE);
        addHeaderView(mHeaderView, null, true);

        mState = IDEL;
        updateHeaderViewByState(-mHeaderView.mViewHeight);
        mLastRefreshTime = DateUtil.getSystemDate(getResources().getString(R.string.pull_view_date_format));
    }

    private void updateHeaderViewByState(int paddingTop) {
        switch (mState) {
            case RELEASE_TO_LOAD:
                mHeaderView.setArrowVisibility(View.VISIBLE);
                mHeaderView.setProgressVisibility(View.GONE);
                mHeaderView.startArrowAnimation(mDownToUpAnimation);
                mHeaderView.setTitleText(R.string.pull_view_release_to_refresh);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                        + " " + mLastRefreshTime);
                break;
            case PULL_TO_LOAD:
                mHeaderView.setArrowVisibility(View.VISIBLE);
                mHeaderView.setProgressVisibility(View.GONE);

                if (mIsBack) {
                    mIsBack = false;
                    mHeaderView.startArrowAnimation(mUpToDownAnimation);
                }
                mHeaderView.setTitleText(R.string.pull_view_pull_to_refresh);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                		+ " " + mLastRefreshTime);
                break;
            case LOADING:
                mHeaderView.setArrowVisibility(View.GONE);
                mHeaderView.setProgressVisibility(View.VISIBLE);
                mHeaderView.startArrowAnimation(null);
                mHeaderView.setTitleText(R.string.pull_view_refreshing);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                		+ " " + mLastRefreshTime);
                break;
            case IDEL:
                mHeaderView.setProgressVisibility(View.GONE);
                mHeaderView.startArrowAnimation(null);
                mHeaderView.setTitleText(R.string.pull_view_pull_to_refresh);
                mHeaderView.setLabelText(getResources().getString(R.string.pull_view_refresh_time)
                		+ " " + mLastRefreshTime);
                break;
            default:
                break;
        }
        mHeaderView.setVisibility(mEnablePullRefresh ? View.VISIBLE : View.INVISIBLE);
        mHeaderView.setLabelVisibility(mHeaderLabelVisibility);
        mHeaderView.setPadding(0, paddingTop, 0, 0);
    }
}
