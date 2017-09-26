package com.mango.player.util;

import com.orhanobut.logger.Logger;

/**
 * Created by lenovo on 2017/2/8.
 */
public class LogUtil {
    private static final String TAG = "LogUtil";

    public static void logByD(String msg) {
        if (msg != null && ApplicationConstant.DEBUG) {
            Logger.t(TAG).d(msg);
        }
    }

    public static void logByD(String tag, String msg) {
        if (msg != null && ApplicationConstant.DEBUG) {
            Logger.t(tag).d(msg);
        }
    }

    public static void logByW(String msg) {
        if (msg != null && ApplicationConstant.DEBUG) {
            Logger.t(TAG).w(msg);
        }
    }

    public static void logByW(String tag, String msg) {
        if (msg != null && ApplicationConstant.DEBUG) {
            Logger.t(tag).w(msg);
        }
    }

    public static void logByW(String tag, String msg, Throwable tr) {
        if (msg != null && ApplicationConstant.DEBUG) {
            Logger.t(tag).w(msg);
        }
    }

    public static void logByE(String str) {
        if (str != null && ApplicationConstant.DEBUG)
            Logger.e(str);
    }
}
