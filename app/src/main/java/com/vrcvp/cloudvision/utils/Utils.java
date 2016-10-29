package com.vrcvp.cloudvision.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

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
}
