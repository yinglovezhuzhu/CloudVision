
package com.xunda.cloudvision.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Http请求缓存数据库帮助类
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */
public class HttpCacheDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "http_cache.db";

	private static final int DB_VERSION = 1;

	public static HttpCacheDBHelper mDBHelper = null;

	public HttpCacheDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public static HttpCacheDBHelper getInstance(Context context) {
		if(mDBHelper == null) {
			mDBHelper = new HttpCacheDBHelper(context);
		}
		return mDBHelper;
	}
	
	public static SQLiteDatabase getReadableDatabase(Context context) {
		return new HttpCacheDBHelper(context).getReadableDatabase();
	}
	
	public static SQLiteDatabase getWriteableDatabase(Context context) {
		return new HttpCacheDBHelper(context).getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//接口Http请求缓存表
		db.execSQL("CREATE TABLE IF NOT EXISTS http_cache(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "url TEXT, corporate TEXT, data TEXT, update_time INTEGER)");

	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropAllTables(db);
		onCreate(db);
	}
	
	private void dropAllTables(SQLiteDatabase db) {
		db.execSQL("drop table if exists download_log");
		
		onCreate(db);
	}
}
