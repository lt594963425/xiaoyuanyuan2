package com.android.library.utils;

import android.util.Log;

import com.android.library.BuildConfig;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class LogUtils {
    private static boolean IS_DEBUG = BuildConfig.DEBUG;
    private static String TAG = "LogUtil";
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();

    public static void i(String tag, String message) {

        if (IS_DEBUG) {
            Log.i(tag + "-->:" + getTargetStackTraceElement(), message);
        }
    }

    public static void e(String tag, String message) {

        if (IS_DEBUG) {
            Log.e(tag + "-->" + getTargetStackTraceElement(), message);
        }
    }

    public static void e(String tag, String message, Throwable tr) {

        if (IS_DEBUG) {
            Log.e(tag + "-->" + getTargetStackTraceElement(), message, tr);
        }
    }

    public static void w(String tag, String message) {

        if (IS_DEBUG) {
            Log.w(tag + "-->:" + getTargetStackTraceElement(), message);
        }
    }

    public static void v(String tag, String message) {

        if (IS_DEBUG) {
            Log.v(tag + "-->:" + getTargetStackTraceElement(), message);
        }
    }

    public static void d(String tag, String message) {

        if (IS_DEBUG) {
            Log.d(tag + "-->:" + getTargetStackTraceElement(), message);
        }
    }

    private static String getTargetStackTraceElement() {
        StackTraceElement targetStackTrace = null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 4) {
            targetStackTrace = stackTrace[4];
        }
        String s = "";
        if (null != targetStackTrace) {
            s = "(" + targetStackTrace.getFileName() + ":"
                    + targetStackTrace.getLineNumber() + ")";
        }
        return s;
    }

}
