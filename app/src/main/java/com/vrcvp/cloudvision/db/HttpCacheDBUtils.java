
package com.vrcvp.cloudvision.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vrcvp.cloudvision.utils.StringUtils;

/**
 * Http请求缓存数据库工具类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */
public class HttpCacheDBUtils {
	
	private static final String TABLE_NAME_CACHE = "http_cache";

	private static final String _ID = "_id";
    private static final String URL = "url";
    private static final String KEY = "key";
    private static final String DATA = "data";
	private static final String UPDATE_TIME = "update_time";

    /**
     * 保存缓存数据，如果已经有记录，更新数据，如果没有，插入新数据
     * @param context Context对象
     * @param url 接口地址url
     * @param key 接口请求标识
     * @param data 接口请求数据
     * @return
     */
	public static long saveHttpCache(Context context, String url, String key, String data) {
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(key)) {
            return -1;
        }
		SQLiteDatabase db = HttpCacheDBHelper.getWriteableDatabase(context);
		long id = -1;
		db.beginTransaction();
        Cursor cursor = null;
		try {
			ContentValues values = new ContentValues();
            values.put(URL, url);
            values.put(KEY, key);
            values.put(DATA, data);
            values.put(UPDATE_TIME, System.currentTimeMillis());
            // 查询记录
            cursor = db.query(TABLE_NAME_CACHE, null, URL + " = ? AND " + KEY + " = ?",
					new String [] {url, key, }, null, null, null);
            if(null != cursor) {
                if(cursor.moveToFirst()) {
                    // 有记录，更新记录
                    id = db.update(TABLE_NAME_CACHE, values, URL + " = ? AND " + KEY + " = ?",
                            new String [] {url, key, });
                } else {
                    // 无记录，插入新纪录
                    id = db.insert(TABLE_NAME_CACHE, "", values);
                }
                cursor.close();
                cursor = null;
            } else {
                // 无记录，插入新纪录
                id = db.insert(TABLE_NAME_CACHE, "", values);
            }
			// 设置事务执行的标志为成功
			db.setTransactionSuccessful();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
            if(null != cursor) {
                cursor.close();
            }
			db.close();
		}
		return id;
	}
	
	/**
	 * 删除缓存记录
	 * @param context Context
	 * @param url 接口URL
     * @param key 接口请求标识
	 * @return 删除记录数
	 */
	public static int deleteHttpCache(Context context, String url, String key) {
		SQLiteDatabase db = HttpCacheDBHelper.getWriteableDatabase(context);
		int count = 0;
		try {
			db.beginTransaction();
			count = db.delete(TABLE_NAME_CACHE, URL + " = ? AND " + KEY + " = ?",
                    new String[] {url, key, });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}

    /**
     * 获取缓存数据
     * @param context Context对象
     * @param url 接口地址
     * @param key 接口请求标识
     * @return 缓存的上一次成功请求的数据
     */
    public static String getHttpCache(Context context, String url, String key) {
		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(key)) {
			return null;
		}
        SQLiteDatabase db = HttpCacheDBHelper.getReadableDatabase(context);
        Cursor cursor = null;
        try {
            // 查询记录
            cursor = db.query(TABLE_NAME_CACHE, null, URL + " = ? AND " + KEY + " = ?",
                    new String [] {url, key, }, null, null, null);
            if(null != cursor) {
                if(cursor.moveToFirst()) {
                    // 有记录，更新记录
                    return cursor.getString(cursor.getColumnIndex(DATA));
                }
                cursor.close();
                cursor = null;
            }
            // 设置事务执行的标志为成功
        } catch(IllegalStateException e) {
            e.printStackTrace();
        } finally {
            if(null != cursor) {
                cursor.close();
            }
            db.close();
        }
        return null;
    }
}
