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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


/**
 * 功能：天气数据库
 * @author yinglovezhuzhu@gmail.com
 *
 */
public class WeatherDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "weather.db";

	private static final int DB_VERSION = 1;

	public static WeatherDBHelper mDBHelper = null;

	public WeatherDBHelper(Context context) {
		super(context, Environment.getExternalStorageDirectory().getPath() + "/" + DB_NAME, null, DB_VERSION);
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
	
	private void dropAllTables(SQLiteDatabase db) {
		onCreate(db);
	}

}
