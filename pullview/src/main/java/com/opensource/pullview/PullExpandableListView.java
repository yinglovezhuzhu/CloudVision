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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.opensource.pullview.utils.DateUtil;

/**
 * Pull to refresh ExpandableListView
 * Created by yinglovezhuzhu@gmail.com on 2015/10/8.
 */
public class PullExpandableListView extends ExpandableListView implements IPullView, AbsListView.OnScrollListener {

    private RotateAnimation mDownToUpAnimation;
    private RotateAnimation mUpToDownAnimation;

    private PullHeaderView mHeaderView;
    private PullFooterView mFooterView;

    private String mLastRefreshTime = "";
    private int mHeaderLabelVisibility = View.VISIBLE;

    private int mFirstItemIndex;
    private int mVisibleItemCount;
    private int mTotalItemCount;

    private int mVerticalScrollOffset = 0;
    private int mVerticalScrollExtent = 0;
    private int mVerticalScrollRange = 0;

    /**
     * Whether it can refresh.
     */
    private boolean mEnablePullRefresh = false;
    /**
     * Whether it can load more data.
     */
    private boolean mEnableLoadMore = false;
    /**
     * Is refreshing data
     */
    private boolean mRefreshing = false;
    /**
     * Can be over scroll *
     */
    private boolean mEnableOverScroll = true;

    private LoadMode mLoadMode = LoadMode.AUTO_LOAD;

    private int mState = IDEL;

    private OnRefreshListener mRefreshListener;
    private OnLoadMoreListener mLoadMoreListener;
    private OnScrollListener mScrollListener;

    public PullExpandableListView(Context context) {
        super(context);
        initView(context, null);
    }

    public PullExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PullExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstItemIndex = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        mTotalItemCount = totalItemCount;
        mVerticalScrollOffset = computeVerticalScrollOffset();
        mVerticalScrollExtent = computeVerticalScrollExtent();
        mVerticalScrollRange = computeVerticalScrollRange();

        if (null != mScrollListener) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE
                && mVerticalScrollRange == mVerticalScrollOffset + mVerticalScrollExtent
                && mState == IDEL) {
            if (mEnableLoadMore && mLoadMode == LoadMode.AUTO_LOAD) {
                setSelection(mTotalItemCount);
                loadMore();
                mState = LOADING;
                updateFooterViewByState(0);
            }
        }
        if (null != mScrollListener) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    private int mStartY;
    private boolean mRecording = false;
    private boolean mIsBack = false;


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
                } else if (mVerticalScrollRange > mVerticalScrollExtent && mFirstItemIndex + mVisibleItemCount == mTotalItemCount) {
                    if (!mRecording) {
                        mRecording = true;
                        mStartY = tempY;
                    }
                    int scrollY = Math.abs(moveY) / OFFSET_RATIO;
                    if (mState != LOADING
                            && (mLoadMode == LoadMode.PULL_TO_LOAD && (mEnableLoadMore || mEnableOverScroll)
                            || mLoadMode == LoadMode.AUTO_LOAD && !mEnableLoadMore && mEnableOverScroll)) {
                        //可以向上pull的条件是
                        //1.mState != LOADING，即非LOADING状态下
                        //2.mLoadMode == LoadMode.PULL_TO_LOAD时有更多数据可加载或者可以过度滑动（OverScroll）
                        // 或者mLoadMode == LoadMode.AUTO_LOAD时没有更多数据可加载但可以过度滑动（OverScroll）

                        // Ensure that the process of setting padding, current position has always been at the footer,
                        // or if when the list exceeds the screen, then, when the push up, the list will scroll at the same time
                        switch (mState) {
                            case RELEASE_TO_LOAD: // release-to-load
                                if(mFirstItemIndex > 0) {
                                    setSelection(mTotalItemCount);
                                }
                                // Slide down, header part was covered, but not all be covered(Pull down to cancel)
                                if (moveY < 0 && scrollY <= mFooterView.mViewHeight) {
                                    mState = PULL_TO_LOAD;
                                } else if (moveY >= 0 && mFirstItemIndex > 0) { //Slide up(Pull up to make footer to show)
                                    mState = IDEL;
                                }
                                updateFooterViewByState(scrollY - mFooterView.mViewHeight);
                                break;
                            case PULL_TO_LOAD:
                                if(mFirstItemIndex > 0) {
                                    setSelection(mTotalItemCount);
                                }
                                // Pull up to the state can enter RELEASE_TO_REFRESH
                                if (scrollY > mFooterView.mViewHeight) {
                                    mState = RELEASE_TO_LOAD;
                                    mIsBack = true;
                                } else if (moveY >= 0) {
                                    mState = IDEL;
                                }
                                updateFooterViewByState(scrollY - mFooterView.mViewHeight);
                                break;
                            case IDEL:
                                if (moveY < 0) {
                                    mState = PULL_TO_LOAD;
                                }
                                updateFooterViewByState(-mFooterView.mViewHeight);
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mStartY = (int) event.getY();
                if (!mRecording) {
                    mRecording = mVerticalScrollRange == mVerticalScrollOffset + mVerticalScrollExtent;
                }
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
                                refresh();
                                mState = LOADING;
                                updateHeaderViewByState(0);
                            } else {
                                mState = IDEL;
                                updateHeaderViewByState(-mHeaderView.mViewHeight);
                            }
                            break;
                        default:
                            break;
                    }
                } else if (mFirstItemIndex + mVisibleItemCount >= mTotalItemCount) {
                    switch (mState) {
                        case IDEL:
                            //Do nothing.
                            break;
                        case PULL_TO_LOAD:
                            //Pull to load more data.
                            mState = IDEL;
                            updateFooterViewByState(-mFooterView.mViewHeight);
                            break;
                        case RELEASE_TO_LOAD:
                            if (mEnableLoadMore) {
                                //Release to load more data.
                                loadMore();
                                mState = LOADING;
                                updateFooterViewByState(0);
                            } else {
                                mState = IDEL;
                                updateFooterViewByState(-mFooterView.mViewHeight);
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
    public void setOnScrollListener(OnScrollListener l) {
        this.mScrollListener = l;
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
     * Show loading view on foot<br>
     * <br><p>Use this method when header view was added on PullListView.
     *
     * @param text
     */
    public void onFootLoading(CharSequence text) {
        mState = LOADING;
        mFooterView.setPadding(0, 0, 0, 0);
        mFooterView.setArrowVisibility(View.GONE);
        mFooterView.setProgressVisibility(View.VISIBLE);
        mFooterView.setTitileVisibility(View.VISIBLE);
        mFooterView.startArrowAnimation(null);
        mFooterView.setTitleText(text);
        mFooterView.setVisibility(View.VISIBLE);
    }

    /**
     * Show loading view on foot<br>
     * <br><p>Use this method when header view was added on PullListView.
     *
     * @param resId
     */
    public void onFootLoading(int resId) {
        mState = LOADING;
        mFooterView.setPadding(0, 0, 0, 0);
        mFooterView.setArrowVisibility(View.GONE);
        mFooterView.setProgressVisibility(View.VISIBLE);
        mFooterView.setTitileVisibility(View.VISIBLE);
        mFooterView.startArrowAnimation(null);
        mFooterView.setTitleText(resId);
        mFooterView.setVisibility(View.VISIBLE);
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
     * Update footer view by state
     *
     * @param paddingBottom
     */
    private void updateFooterViewByState(int paddingBottom) {
        switch (mState) {
            case RELEASE_TO_LOAD:
                mFooterView.setArrowVisibility(View.VISIBLE);
                mFooterView.setProgressVisibility(View.GONE);
                mFooterView.startArrowAnimation(mDownToUpAnimation);
                mFooterView.setTitleText(R.string.pull_view_release_to_load);
                break;
            case PULL_TO_LOAD:
                mFooterView.setArrowVisibility(View.VISIBLE);
                mFooterView.setProgressVisibility(View.GONE);

                if (mIsBack) {
                    mIsBack = false;
                    mFooterView.startArrowAnimation(mUpToDownAnimation);
                }
                mFooterView.setTitleText(R.string.pull_view_pull_to_load);
                break;
            case LOADING:
                mFooterView.setArrowVisibility(View.GONE);
                mFooterView.setProgressVisibility(View.VISIBLE);
                mFooterView.startArrowAnimation(null);
                mFooterView.setTitleText(R.string.pull_view_loading);
                break;
            case IDEL:
                mFooterView.setProgressVisibility(View.GONE);
                mFooterView.startArrowAnimation(null);
                mFooterView.setTitleText(R.string.pull_view_pull_to_load);
                break;
            default:
                break;
        }
        mFooterView.setVisibility(mEnableLoadMore ? View.VISIBLE : View.INVISIBLE);
        mFooterView.setPadding(0, 0, 0, paddingBottom);
    }

    /**
     * Refresh data complete
     */
    public void refreshCompleted() {
        mState = IDEL;
        mRefreshing = false;
        mRecording = false;mRecording = false;
        mIsBack = false;
        mLastRefreshTime = DateUtil.getSystemDate(getResources().getString(R.string.pull_view_date_format));
        updateHeaderViewByState(-mHeaderView.mViewHeight);
    }

    /**
     * Load more complete
     */
    public void loadMoreCompleted(boolean canLoadmore) {
        mState = IDEL;
        mRefreshing = false;
        mRecording = false;
        this.mEnableLoadMore = null != mLoadMoreListener && canLoadmore;
        updateFooterViewByState(-mFooterView.mViewHeight);
    }

    /**
     * Sets listener to listen refresh action
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
        mEnablePullRefresh = null != listener;
    }

    /**
     * Sets listener to listen load more action
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
        mEnableLoadMore = null != listener;
    }

    /**
     * Sets the mode to load more data.<br>
     * <p>can use value is {@link LoadMode#AUTO_LOAD}
     * and {@link LoadMode#PULL_TO_LOAD}<br>
     * default is {@link LoadMode#AUTO_LOAD}
     *
     * @param mode
     * @see {@link com.opensource.pullview.IPullView.LoadMode}
     */
    public void setLoadMode(LoadMode mode) {
        this.mLoadMode = mode;
    }

    /**
     * Sets the pull listview can over scroll or not.
     *
     * @param enable
     */
    public void setEnableOverScroll(boolean enable) {
        this.mEnableOverScroll = enable;
    }

    /**
     * Gets it is refreshing<br/>
     * </br><p/>If doing refresh operation, you need to use this method before {@link #refreshCompleted()}<br/>
     * to get it is refresh operation or not.
     *
     * @return
     */
    public boolean isRefreshing() {
        return mRefreshing;
    }

    /**
     * Can load more or not
     * @return
     */
    public boolean canLoadMore() {
        return mEnableLoadMore;
    }

    private void initView(Context context, AttributeSet attrs) {
        if(null != attrs) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullView);
            if(a.hasValue(R.styleable.PullView_loadMode)) {
                int loadMoreMode = a.getInt(R.styleable.PullView_loadMode, LoadMode.AUTO_LOAD.value());
                LoadMode loadMode = LoadMode.valueOf(loadMoreMode);
                if(null != loadMode) {
                    setLoadMode(loadMode);
                }
            }
            if(a.hasValue(R.styleable.PullView_overScroll)) {
                mEnableOverScroll = a.getBoolean(R.styleable.PullView_overScroll, true);
            }
            a.recycle();
        }

        mDownToUpAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownToUpAnimation.setInterpolator(new LinearInterpolator());
        mDownToUpAnimation.setDuration(ROTATE_ANIMATION_DURATION);
        mDownToUpAnimation.setFillAfter(true);


        mUpToDownAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpToDownAnimation.setInterpolator(new LinearInterpolator());
        mUpToDownAnimation.setDuration(ROTATE_ANIMATION_DURATION);
        mUpToDownAnimation.setFillAfter(true);

        mHeaderView = new PullHeaderView(context);
        mHeaderView.setLabelVisibility(View.VISIBLE);
        addHeaderView(mHeaderView, null, true);

        mState = IDEL;
        updateHeaderViewByState(-mHeaderView.mViewHeight);
        mLastRefreshTime = DateUtil.getSystemDate(getResources().getString(R.string.pull_view_date_format));

        mFooterView = new PullFooterView(context);
        addFooterView(mFooterView, null, true);

        mState = IDEL;
        mRefreshing = false;
        mRecording = false;
        mEnableLoadMore = null != mLoadMoreListener && mEnableLoadMore;
        updateFooterViewByState(-mFooterView.mViewHeight);

        super.setOnScrollListener(this);
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


    /**
     * Do load more operation.
     */
    private void loadMore() {
        if (null == mLoadMoreListener || mState == LOADING) {
            return;
        }
        mRefreshing = false;
        mLoadMoreListener.onLoadMore();
    }

    /**
     * Do refresh operation.
     */
    private void refresh() {
        if (null == mRefreshListener || mState == LOADING) {
            return;
        }
        mRefreshing = true;
        mRefreshListener.onRefresh();
    }
}
