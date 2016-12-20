package com.vrcvp.cloudvision.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.JPushExtra;
import com.vrcvp.cloudvision.bean.JPushMessage;
import com.vrcvp.cloudvision.utils.LogUtils;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * JPush自定义广播接收器
 * Created by yinglovezhuzhu@gmail.com on 2016/10/27.
 */

public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPushReceiver";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtils.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtils.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            JPushMessage message = parseJPushMessage(bundle);
            if(null == message) {
                return;
            }

            final String msgData = message.getMessage();
            if(StringUtils.isEmpty(msgData)) {
                return;
            }

            JPushExtra extraData = null;
            try {
                extraData = new Gson().fromJson(msgData, JPushExtra.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(null == extraData) {
                return;
            }

            switch (extraData.getMessageType()) {
                case JPushExtra.TYPE_CLOSE_LCD_BACKLIGHT:
                    LogUtils.d(TAG, "接收到远程关闭显示器背光指令：" + message.toString());
                    Toast.makeText(context, "接收到远程关闭显示器背光指令", Toast.LENGTH_LONG).show();
                    if(!Utils.smdtIsLCDLightOn(context)) {
                        LogUtils.e(TAG, "设备显示器背光已经关闭");
                    }
//                    Utils.smdtSetLCDLight(context, false);
                    // 延迟3秒，显示Toast
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.smdtSetLCDLight(context, false);
                        }
                    }, 1000 * 3);
                    break;
                case JPushExtra.TYPE_OPEN_LCD_BACKLIGHT:
                    LogUtils.d(TAG, "接收到远程开启显示器背光指令：" + message.toString());
                    Toast.makeText(context, "接收到远程开启显示器背光指令", Toast.LENGTH_LONG).show();
                    if(Utils.smdtIsLCDLightOn(context)) {
                        LogUtils.e(TAG, "设备显示器背光已经开启");
                    }
                    Utils.smdtSetLCDLight(context, true);
                    break;
                default:
                    break;
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtils.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.d(TAG, "[JPushReceiver] 用户点击打开了通知");

            //打开自定义的Activity
//            Intent i = new Intent(context, TestActivity.class);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtils.d(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtils.w(TAG, "[JPushReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            LogUtils.d(TAG, "[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 解析推送数据
     * @param bundle 推送数据
     * @return JPushMessage对象
     */
    private JPushMessage parseJPushMessage(Bundle bundle) {
        if(null == bundle) {
            return null;
        }
        final JPushMessage message = new JPushMessage();
        if(bundle.containsKey(JPushInterface.EXTRA_NOTIFICATION_ID)) {
            message.setId(bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
        }
        if(bundle.containsKey(JPushInterface.EXTRA_TITLE)) {
            message.setTitle(bundle.getString(JPushInterface.EXTRA_TITLE));
        }
        if(bundle.containsKey(JPushInterface.EXTRA_MESSAGE)) {
            message.setMessage(bundle.getString(JPushInterface.EXTRA_MESSAGE));
        }
        if(bundle.containsKey(JPushInterface.EXTRA_EXTRA)) {
            final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if(!StringUtils.isEmpty(extra)) {
                message.setExtra(extra);
            }
        }
        if(bundle.containsKey(JPushInterface.EXTRA_CONTENT_TYPE)) {
            message.setContentType(bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE));
        }
        if(bundle.containsKey(JPushInterface.EXTRA_APP_KEY)) {
            message.setAppKey(bundle.getString(JPushInterface.EXTRA_APP_KEY));
        }
        return message;
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    LogUtils.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtils.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
