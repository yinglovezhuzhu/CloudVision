package com.vrcvp.cloudvision.ui.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vrcvp.cloudvision.BuildConfig;
import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.ui.widget.LoadingDialog;
import com.vrcvp.cloudvision.utils.StringUtils;

import java.util.List;

/**
 * Activity基类
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {
	
	protected final String TAG = getClass().getSimpleName();

	protected LoadingDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * 显示一个短Toast
     * @param text
     */
    protected void showShortToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个短Toast
     * @param resId
     */
    protected void showShortToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param text
     */
    protected void showLongToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param resId
     */
    protected void showLongToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

	/**
	 * 显示加载框
	 * @param message 消息文字
     */
    public void showLoadingDialog(String message) {
		if(null != mLoadingDialog && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
		mLoadingDialog = LoadingDialog.showLoadingDialog(this, message);
	}

	/**
	 * 显示加载框
	 * @param messageTextResId 消息文字资源id
	 */
    public void showLoadingDialog(int messageTextResId) {
		if(null != mLoadingDialog && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
		if(0 == messageTextResId) {
			mLoadingDialog = LoadingDialog.showLoadingDialog(this, null);
		} else {
			mLoadingDialog = LoadingDialog.showLoadingDialog(this, getString(messageTextResId));
		}
	}

    /**
     * 显示加载框
     * @param message 消息文字
     * @param cancelable 是否可取消
     * @param cancelListener 取消监听
     */
    public void showLoadingDialog(String message, boolean cancelable,
                                     DialogInterface.OnCancelListener cancelListener) {
        if(null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
        mLoadingDialog = LoadingDialog.showLoadingDialog(this, message, cancelable, false, cancelListener);
    }

    /**
     * 显示加载框
     * @param messageTextResId 消息文字资源id
     * @param cancelable 是否可取消
     * @param cancelListener 取消监听
     */
    public void showLoadingDialog(int messageTextResId, boolean cancelable,
                                     DialogInterface.OnCancelListener cancelListener) {
        if(null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
        if(0 == messageTextResId) {
            mLoadingDialog = LoadingDialog.showLoadingDialog(this, null, cancelable, false, cancelListener);
        } else {
            mLoadingDialog = LoadingDialog.showLoadingDialog(this, getString(messageTextResId), cancelable, false, cancelListener);
        }
    }

	/**
	 * 隐藏加载框
	 */
    public void cancelLoadingDialog() {
		if(null != mLoadingDialog && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
		mLoadingDialog = null;
	}

	/**
	 * 隐藏软键盘
	 * @param view View
	 */
    public void hideSoftInputFromWindow(View view) {
		if(null == view) {
			return;
		}
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
		}
	}

	/**
	 * 退出当前Activity
	 * @param resultCode 结果吗
	 * @param extras 额外传递数据
	 */
    public void finish(int resultCode, Bundle extras) {
		Intent data = new Intent();
		if(null != extras && !extras.isEmpty()) {
			data.putExtras(extras);
		}
		setResult(resultCode, data);
		finish();
	}

	/**
	 * 加载图片并且显示
	 * @param path 图片地址
	 * @param imageView 显示图片的ImageView控件
     */
	protected void loadImage(String path, ImageView imageView) {
		loadImage(path, imageView, R.drawable.default_img, R.drawable.default_img);
	}

    /**
     * 加载图片
     * @param path 图片地址
     * @param imageView 显示图片的ImageView
     * @param placeholder 加载占位图
     * @param error 加载错误占位图
     */
	protected void loadImage(String path, ImageView imageView, int placeholder, int error) {
		if( null == imageView) {
			return;
		}
		if(StringUtils.isEmpty(path)) {
			imageView.setImageResource(placeholder);
            return;
		}
        Picasso.with(this)
                .load(path)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }

	/**
	 * 打开网页
	 * @param url
	 */
	protected void openWebView(String url) {
		if(StringUtils.isEmpty(url)) {
			return;
		}
		Intent i = new Intent(this, WebViewActivity.class);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	/**
	 * 播放视频
	 * @param videoUrl 视频url地址
	 * @param name 视频名称
	 */
	protected void playVideo(String videoUrl, String name) {
		if(StringUtils.isEmpty(videoUrl)) {
			return;
		}
		Intent i = new Intent(this, VideoPlayerActivity.class);
		i.setData(Uri.parse(videoUrl));
		i.putExtra(Config.EXTRA_TITLE_STR, name);
		startActivity(i);
	}
}
