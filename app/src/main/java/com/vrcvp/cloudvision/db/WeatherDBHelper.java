/*
 * Copyright (C) 2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.vrcvp.cloudvision.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.vrcvp.cloudvision.Config;
import com.vrcvp.cloudvision.utils.SharedPrefHelper;
import com.vrcvp.cloudvision.utils.StringUtils;
import com.vrcvp.cloudvision.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 功能：天气数据库
 * @author yinglovezhuzhu@gmail.com
 *
 */
public class WeatherDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "weather.db";

    private static final String TB_CITY_CODE = "city_code";

	private static final int DB_VERSION = 1;

	public static WeatherDBHelper mDBHelper = null;

	public WeatherDBHelper(Context context) {
		super(context, getDatabaseFile(context).getPath(), null, DB_VERSION);
	}
	
	public static WeatherDBHelper getInstance(Context context) {
		if(mDBHelper == null) {
			mDBHelper = new WeatherDBHelper(context);
		}
		return mDBHelper;
	}
	
	public static SQLiteDatabase getReadableDatabase(Context context) {
		return new WeatherDBHelper(context).getReadableDatabase();
	}
	
	public static SQLiteDatabase getWriteableDatabase(Context context) {
		return new WeatherDBHelper(context).getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//城市列表
		db.execSQL("CREATE TABLE IF NOT EXISTS city_code(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "code TEXT, province TEXT, city TEXT, region TEXT)");

	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropAllTables(db);
	}

    /**
     * 获取数据库文件
     * @param context Context对象
     * @return 数据库文件
     */
    public static File getDatabaseFile(Context context) {
        File dbFolder = context.getExternalCacheDir();
        if(null == dbFolder) {
            dbFolder = context.getCacheDir();
        }
        dbFolder = new File(dbFolder, "database");
        if(!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
        return new File(dbFolder, DB_NAME);
    }

    /**
     * 检查是否需要复制城市编码数据库文件
     * @param context Context对象
     */
    public static void copyDatabaseFileIfNeeded(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final SharedPrefHelper sharedPrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
                final File dbFile = getDatabaseFile(context);
                boolean needCopy = false;
                if(dbFile.exists()) {
                    final String dbFileMD5 = sharedPrefHelper.getString(Config.SP_KEY_CITY_CODE_DB_FILE_MD5, "");
                    try {
                        String md5 = Utils.getFileMD5(new FileInputStream(dbFile));
                        needCopy = null == dbFileMD5 || !dbFileMD5.equals(md5);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else {
                    needCopy = true;
                }
                if(needCopy) {
                    sharedPrefHelper.saveBoolean(Config.SP_KEY_CITY_CODE_DB_FILE_READY, false);
                    InputStream is = null;
                    FileOutputStream fos = null;
                    try {
                        final MessageDigest md5 = MessageDigest.getInstance("MD5");
                        is = context.getAssets().open(DB_NAME);
                        fos = new FileOutputStream(dbFile);
                        byte[] buffer = new byte[1024 * 16]; // 16KB
                        int length;
                        while ((length = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, length);
                            md5.update(buffer, 0, length);
                        }
                        sharedPrefHelper.saveString(Config.SP_KEY_CITY_CODE_DB_FILE_MD5, Utils.convertByteToHex(md5.digest()));
                        sharedPrefHelper.saveBoolean(Config.SP_KEY_CITY_CODE_DB_FILE_READY, true);
                    } catch (IOException|NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } finally {
                        if(null != fos) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(null != is) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 城市编码数据库文件是否已经准备好
     * @param context  Context对象
     * @return 城市编码数据库文件是否已经准备好， true 已经准备好， false 没有准备好
     */
    public static boolean cityCodeDBFileReady(Context context) {
        final SharedPrefHelper sharedPrefHelper = SharedPrefHelper.newInstance(context, Config.SP_FILE_CONFIG);
        return sharedPrefHelper.getBoolean(Config.SP_KEY_CITY_CODE_DB_FILE_READY, false);
    }

    /**
     * 查询CityCode
     * @param context Context
     * @param cityName 城市名称
     * @return 城市编码
     */
    public static String getCityCode(Context context, String cityName) {
        if(StringUtils.isEmpty(cityName) || !cityCodeDBFileReady(context)) {
            return "";
        }
        if(cityName.endsWith("省")) {
            cityName = cityName.substring(0, cityName.lastIndexOf("省"));
        } else if(cityName.endsWith("市")) {
            cityName = cityName.substring(0, cityName.lastIndexOf("市"));
        } else if(cityName.endsWith("区")) {
            cityName = cityName.substring(0, cityName.lastIndexOf("区"));
        } else if(cityName.endsWith("县")) {
            cityName = cityName.substring(0, cityName.lastIndexOf("县"));
        }

        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase(context);
            cursor = db.query(TB_CITY_CODE, null, "region=?", new String [] {cityName, }, null, null, null);
            if(null != cursor) {
                if(cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex("code"));
                }
            }

            cursor = db.query(TB_CITY_CODE, null, "city=?", new String [] {cityName, }, null, null, null);
            if(null != cursor) {
                if(cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex("code"));
                }
            }

            cursor = db.query(TB_CITY_CODE, null, "province=?", new String [] {cityName, }, null, null, null);
            if(null != cursor) {
                if(cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex("code"));
                }
            }
        } finally {
            if(null != cursor) {
                cursor.close();
            }
            if(null != db) {
                db.close();
            }
        }
        return "";
    }

	private void dropAllTables(SQLiteDatabase db) {
		onCreate(db);
	}

}
