package com.vrcvp.cloudvision.utils;

import android.app.smdt.SmdtManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 工具类
 * Created by yinglovezhuzhu@gmail.com on 2016/9/16.
 */
public class Utils {
    private Utils() {}

    /**
     * Measure a view.
     * @param child
     */
    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 获取全部meta-data数据
     * @param context Context对象
     * @return 存储所有meta-data数据的Bundle对象
     */
    public static Bundle getMetaDatas(Context context) {
        final ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if(null == applicationInfo || null == applicationInfo.metaData) {
                return null;
            }
            return applicationInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取meta-data数据
     * @param context Context对象
     * @param key meta-data key
     * @return String类型的meta-data数据
     */
    public static String getStringMetaData(Context context, String key) {
        final Bundle bundle = getMetaDatas(context);
        if(null == bundle || !bundle.containsKey(key)) {
            return "";
        }
        return bundle.getString(key, "");
    }

    /**
     * 获取字符串的MD5编码
     * @param inputString
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    /**
     * 获取文件MD5
     * @param is InputStream对象
     * @return MD5字符串
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileMD5(InputStream is) throws IOException, NoSuchAlgorithmException{
        if(null == is) {
            return null;
        }
        try {
            byte[] buffer = new byte[1024 * 16]; // 16KB
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            int len;
            while((len = is.read(buffer)) != -1){
                md5.update(buffer, 0, len);
            }
            return convertByteToHex(md5.digest());
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }


    /**
     * 获取手机特征号(设备号)
     * @return 设备特征号，平板等没有的可能返回空
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";
        try {
            TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                deviceId = telephonyManager.getDeviceId();
            } else {
                try {
                    deviceId = telephonyManager.getDeviceId(telephonyManager.getPhoneType());
                } catch (NoSuchMethodError e) {
                    deviceId = telephonyManager.getDeviceId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null == deviceId ? "" : deviceId;
    }

    /**
     * 生成设备id
     * @param context Context对象
     * @return 设备唯一标识
     */
    public static String getClientId(Context context) {
        SharedPrefHelper sharedPrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
        String clientId = sharedPrefHelper.getString(Config.SP_KEY_CLIENT_ID, "");
        if ("".equals(clientId)) {
            String androidId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            try {
                String deviceId = getDeviceId(context);
                if (null != androidId && !"9774d56d682e549c".equals(androidId)
                        && null != deviceId && !"".equals(deviceId)
                        && !"000000000000000".equals(deviceId)) {
                    clientId = getMD5Hex(androidId + deviceId);
                } else if (null != androidId && !"9774d56d682e549c".equals(androidId)) {
                    clientId = getMD5Hex(androidId);
                } else if (null != deviceId && !"".equals(deviceId)
                        && !"000000000000000".equals(deviceId)) {
                    clientId = getMD5Hex(deviceId);
                } else {
                    clientId = getMD5Hex(context.getPackageName()
                            + new Random().nextLong() + System.currentTimeMillis());
                }
            } catch (NoSuchAlgorithmException e) {
                // 做最后的保障处理,基本不会到这里
                clientId = String.valueOf(System.currentTimeMillis());
            }
            sharedPrefHelper.saveString(Config.SP_KEY_CLIENT_ID, clientId);
        }
        return clientId;
    }

    /**
     * 获取MAC地址
     * @param context Context对象
     * @return MAC网卡地址，可能为空
     */
    public static String getMac(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(null == wifiManager) {
            return "";
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(null == wifiInfo) {
            return "";
        }
        return wifiInfo.getMacAddress();
//        SmdtManager smdtManager = SmdtManager.create(context);
//        return null == smdtManager ? null : smdtManager.smdtGetEthMacAddress();
    }

    /**
     * 关闭系统
     * @param context Context对象
     */
    public static void smdtShutdownSystem(Context context) {
        SmdtManager smdtManager = SmdtManager.create(context);
        if(null == smdtManager) {
            return;
        }
        smdtManager.shutDown();
    }

    /**
     * 重启系统
     * @param context Context对象
     * @param reason 原因描述
     */
    public static void smdtRebootSystem(Context context, String reason) {
        SmdtManager smdtManager = SmdtManager.create(context);
        if(null == smdtManager) {
            return;
        }
        if(StringUtils.isEmpty(reason)) {
            smdtManager.smdtReboot();
        } else {
            smdtManager.smdtReboot(reason);
        }
    }

    /**
     * 设置屏幕背光
     * @param context Context对象
     * @param open 是否开启
     */
    public static void smdtSetLCDLight(Context context, boolean open) {
//        SmdtManager smdtManager = SmdtManager.create(context);
//        if(null == smdtManager) {
//            return;
//        }
//        smdtManager.smdtSetLcdBackLight(open ? 1 : 0);
    }

    /**
     * 静默安装apk
     * @param context Context对象
     * @param apkPath apk文件路径（必须保证apk存在并且完整可用）
     */
    public void smdtSilentInstallApk(Context context, String apkPath) {
        SmdtManager smdtManager = SmdtManager.create(context);
        if(null == smdtManager) {
            return;
        }
        smdtManager.smdtSilentInstall(apkPath, context);
    }

    /**
     * 将byte数组转换成16进制字符串
     * @param byteData byte数组
     * @return 16进制字符串
     */
    public static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    /**
     * 解析时间
     * @param time 时间
     * @param pattern 时间格式
     * @return Date对象
     */
    public static Date parseTime(String time, String pattern) {
        if(StringUtils.isEmpty(time) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取打印时间
     * @param context Context对象
     * @param time 时间毫秒
     * @return 打印结果
     */
    public static String printTime(Context context, long time) {
        if(time < 0) {
            return String.format(Locale.getDefault(), context.getString(R.string.str_time_mills_format), 0);
        }

        long t = time;
        if(t / 1000 == 0) {
            return String.format(Locale.getDefault(), context.getString(R.string.str_time_mills_format), t);
        }

        t /= 1000;
        if(t / 60 == 0) {
            return String.format(Locale.getDefault(), context.getString(R.string.str_time_second_format), t);
        }

        t /= 60;
        if(t / 60 == 0) {
            return String.format(Locale.getDefault(), context.getString(R.string.str_time_minutes_format), t);
        }

        t /= 60;
        if(t / 24 == 0) {
            return String.format(Locale.getDefault(), context.getString(R.string.str_time_hour_format), t);
        }

        t /= 24;
        return String.format(Locale.getDefault(), context.getString(R.string.str_time_day_format), t);
    }
}
