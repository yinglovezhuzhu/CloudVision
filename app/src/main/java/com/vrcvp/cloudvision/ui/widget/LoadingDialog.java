package com.vrcvp.cloudvision.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vrcvp.cloudvision.R;

/**
 * 自定义的加载框
 * Create by yinglovezhuzhu@gmail.com on 2015/11/09
 */
public class LoadingDialog extends Dialog {

    private TextView mTvMessage;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialogTheme);
        initView(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    public void setMessage(CharSequence message) {
        if(null == message || message.length() == 0) {
            mTvMessage.setVisibility(View.GONE);
            return;
        }
        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(message);
    }

    /**
     * 返回一个自定义的加载框，可配置显示消息，默认可以取消，点击外部不会取消
     * @param context Context
     * @param message ic_message_tab_normal
     * @return LoadingDialog 对象
     */
    public static LoadingDialog showLoadingDialog(Context context, String message) {
        return showLoadingDialog(context, message, true, false, null);
    }

    /**
     * 返回一个自定义加载框对象，可配置消息，是否可取消，点击外部是否可以取消，消失监听
     * @param context Context
     * @param message 可配置消息
     * @param cancelable 是否可取消
     * @param canceledOnTouchOutside 点击外部是否可以取消
     * @param cancelListener 消失监听
     * @return LoadingDialog 对象
     */
    public static LoadingDialog showLoadingDialog(Context context, String message, boolean cancelable,
                                           boolean canceledOnTouchOutside,
                                           OnCancelListener cancelListener) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setMessage(message);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void initView(Context context) {

        setContentView(R.layout.layout_loading_dialog);

        mTvMessage = (TextView) findViewById(R.id.tv_loading_dialog_message);
    }
}
