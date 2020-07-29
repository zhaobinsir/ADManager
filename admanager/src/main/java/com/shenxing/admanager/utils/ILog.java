package com.shenxing.admanager.utils;

import android.util.Log;

/**
 * Created by zhaobinsir
 * on 2020/7/29.
 * log 输出总开关
 */
public class ILog {

    public static boolean DEBUG = true;

    public static void d(String tag, String info) {
        if (DEBUG) Log.d(tag, info);

    }
    public static void e(String tag, String info) {
        if (DEBUG) Log.e(tag, info);
    }

    public static void i(String tag, String info) {
        if (DEBUG) Log.i(tag, info);
    }

    public static void w(String tag, String info) {
        if (DEBUG) Log.w(tag, info);
    }
}
