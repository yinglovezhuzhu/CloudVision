/*
 * Copyright (C)2016. The Android Open Source Project.
 *
 *          yinglovezhuzhu@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */	

package com.opensource.pullview.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Use:
 * Created by yinglovezhuzhu@gmail.com on 2014-06-06.
 */
public class DateUtil {
	
    private DateUtil() {}

    /**
     * Get system date, with format by pattern.
     * @param pattern The format of date string.
     * @return
     */
    public static String getSystemDate(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    /**
     * Get system year.
     * @return Ssytem year
     */
    public static int getYear() {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        return c.get(Calendar.YEAR);
    }

    /**
     * Get system years<br>
     * <p>
     * @param backStep Fallback years
     * @param minYear The min year
     * @return
     */
    public static List<Integer> getYears(int backStep, int minYear) {
        List<Integer> years = new ArrayList<Integer>();
        int year = getYear();
        for (int i = 0; i < backStep; i++) {
            if(year - i < minYear) {
                break;
            }
            years.add(year - i);
        }
        return years;
    }

    /**
     * Get yesterday system date.
     * @param pattern
     * @return
     */
    public static String getYesterdayDate(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Parse date from string to  milliseconds
     * @param pattern
     * @param date
     * @return  milliseconds if parse success, 0 failed.
     */
    public static long parseDate(String pattern, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Format time
     * @param pattern
     * @param timeMillis
     * @return
     */
    public static String formatDate(String pattern, long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date(timeMillis));
    }

    /**
     * Change format srcPattern from to distPattern<br>
     * <p>Description: If precision changed when change format, only support 
     * high precision to low precision.<br>
     * etc: It is success change "yyyy-MM-dd HH:mm:ss" to "yyyy-MM-dd", <br>
     * but it would be got wrong result when change "yyyy-MM-dd" to "yyyy-MM-dd HH:mm:ss".
     * @param source
     * @param srcPattern
     * @param distPattern
     * @return The time whit new format as string or origin time if exception happened.
     */
    public static String changeFormat(String source, String srcPattern, String distPattern) {
        SimpleDateFormat srcFormt = new SimpleDateFormat(srcPattern, Locale.getDefault());
        SimpleDateFormat distFormt = new SimpleDateFormat(distPattern, Locale.getDefault());
        try {
            return distFormt.format(srcFormt.parse(source));
        } catch (ParseException e) {
            e.printStackTrace();
            return source;
        }
    }

    /**
     * Get system time as hour.
     * @return
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
