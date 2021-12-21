package com.example.domain.util;

import android.util.Log;

/**
 * Created by sev_user on 11/16/2016.
 */

public class Debug {

    private static final String TAG = "Brain RDT";

    private static void updateLogScreen(String log){
//        mApp = TvContentSyncApplication.getInstance();
//        mApp.updateLogScreen(log);
    }

    public static void log(String text) {
        Log.d(TAG + convertText(), convertMethod() + text);
        updateLogScreen(convertMethod() + text);
    }

    public static void logV(String text) {
        Log.v(TAG + convertText(), convertMethod() + text);
        updateLogScreen(convertMethod() + text);
    }

    public static <T> void logV(T...ts) {
        StringBuffer buffer = new StringBuffer();
        for (T t : ts){
            buffer.append(t + ", ");
        }

        Log.v(TAG + convertText(), convertMethod() + buffer.toString());
        updateLogScreen(convertMethod() + buffer.toString());
    }

    public static void log() {
        Log.d(TAG + convertText(), convertMethod());
        updateLogScreen(convertMethod());
    }

    public static <T> void log(T...ts) {
        StringBuffer buffer = new StringBuffer();
        for (T t : ts){
            buffer.append(t + ", ");
        }

        Log.d(TAG + convertText(), convertMethod() + buffer.toString());
        updateLogScreen(convertMethod() + buffer.toString());
    }

    public static void logI(String text) {
        Log.i(TAG + convertText(), convertMethod() + text);
        updateLogScreen(convertMethod() + text);

    }

    public static <T> void logI(T...ts) {
        StringBuffer buffer = new StringBuffer();
        for (T t : ts){
            buffer.append(t + ", ");
        }

        Log.i(TAG + convertText(), convertMethod() + buffer.toString());
        updateLogScreen(convertMethod() + buffer.toString());

    }

    public static void logW(String text) {
        Log.w(TAG + convertText(), convertMethod() + text);
        updateLogScreen(convertMethod() + text);

    }

    public static void logE(String text) {
        Log.e(TAG + convertText(), convertMethod() + text);
        updateLogScreen(convertMethod() + text);

    }

    public static void logE(Exception e) {
        Log.e(TAG + convertText(), convertMethod() + e.getLocalizedMessage());
        e.printStackTrace();
        updateLogScreen(convertMethod() + e.getLocalizedMessage());
    }

    public static void logW(Exception e) {
        Log.w(TAG + convertText(), convertMethod() + e.getLocalizedMessage());
        e.printStackTrace();
        updateLogScreen(convertMethod() + e.getLocalizedMessage());
    }

    public static void log(String subTag, String text) {
        Log.d(TAG + convertText(), convertMethod() + subTag + " " + text);
        updateLogScreen(convertMethod() + subTag + " " + text);
    }

    public static void logW(String subTag, String text) {
        Log.w(TAG + convertText(), convertMethod() + subTag + " " + text);
        updateLogScreen(convertMethod() + subTag + " " + text);

    }

    public static void logE(String subTag, String text) {
        Log.e(TAG + convertText(), convertMethod() + subTag + " " + text);
        updateLogScreen(convertMethod() + subTag + " " + text);

    }

    public static void logW(String text, Exception ex) {
        Log.w(TAG + convertText(), convertMethod() + text + " " + ex);
        updateLogScreen(convertMethod() + text + " " + ex);

    }

    public static void logW(String text, String subText, Exception ex) {
        Log.w(TAG + convertText(), convertMethod() + text + " " + subText + " " + ex);
        updateLogScreen(convertMethod() + text + " " + subText + " " + ex);

    }

    public static void logE(String text, Exception ex) {
        Log.e(TAG + convertText(), convertMethod() + text + " " + ex);
        updateLogScreen(convertMethod() + text + " " + ex);
    }

    public static void logD(String text) {
        Log.d(TAG + convertText(), convertMethod() + text);
        updateLogScreen(convertMethod() + text);
    }

    public static void logD(String subTag, String text) {
        Log.d(TAG + convertText(), convertMethod() + subTag + " " + text);
        updateLogScreen(convertMethod() + subTag + " " + text);
    }

    private static String convertText() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        String[] fileName = stackTrace.getFileName().split(".java");
        return "_" + fileName[0];
    }

    private static String convertMethod() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        return "[" + stackTrace.getLineNumber() + ":" + stackTrace.getMethodName() + "] ";
    }
}

