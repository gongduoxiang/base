package com.yc.basis.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

public class LogMonitor {
    private static LogMonitor INSTANCE = new LogMonitor();
    private HandlerThread handlerThread = new HandlerThread("log");
    private Handler handler;
    private static final long TIME_BLOCK = 1000L;
    private LogMonitor() {
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }
    private static Runnable printRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder builder = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                builder.append(element.toString() + "\n");
            }
            Log.e("gdx", builder.toString());
        }
    };
    public static LogMonitor getInstance() {
        return INSTANCE;
    }
    public void startMonitor() {
        handler.postDelayed(printRunnable, TIME_BLOCK);
    }
    public void removeMonitor() {
        handler.removeCallbacks(printRunnable);
    }
}
