package com.xunda.cloudvision.presenter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yinglovezhuzhu@gmail.com on 2016/8/19.
 */
public class BasePresenter {
    /**
     * 显示一个短Toast
     * @param text
     */
    protected void showShortToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个短Toast
     * @param resId
     */
    protected void showShortToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param text
     */
    protected void showLongToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param resId
     */
    protected void showLongToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
