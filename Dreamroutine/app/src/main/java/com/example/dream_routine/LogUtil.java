package com.example.dream_routine;

import android.util.Log;

public class LogUtil {
    public static final boolean WRITELOG = true;

    public static void LogD(String tag, String msg) {
        if(WRITELOG) {
            Log.d(tag, msg);
        }
    }

    public static void LogE(String tag, String msg) {
        if(WRITELOG) {
            Log.e(tag, msg);
        }
    }

    public static void LogI(String tag, String msg) {
        if(WRITELOG) {
            Log.i(tag, msg);
        }
    }

    public static void LogW(String tag, String msg) {
        if(WRITELOG) {
            Log.w(tag, msg);
        }
    }
}
