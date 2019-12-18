package com.engineerai.android.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.engineerai.android.R;


public class Util
{
    private static Dialog dialog;

    //log with tag and value
    public static void log(String tag, String value) {
      //  if (BuildConfig.IS_DEBUG)
            Log.e(tag + "", value + "");
    }

    //log with value
    public static void log(String value) {
        //if (BuildConfig.IS_DEBUG)
            Log.e("TestExam", value + "");
    }

    //log with exception value
    public static void log(String value, Throwable e) {
       // if (BuildConfig.IS_DEBUG)
            Log.e("TestExam", value + "", e);
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void showProgress(Context context, boolean cancellable) {
        if (context == null)
            return;

        if (checkProgressOpen())
            return;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(cancellable);
        try {
            dialog.show();
        } catch (Exception e) {
            Util.log("Exception ", e);
        }
    }

    public static boolean checkProgressOpen() {
        if (dialog != null && dialog.isShowing())
            return true;
        else
            return false;
    }

    public static void cancelProgress() {
        if (checkProgressOpen()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                Util.log("Exception ", e);
            }
            dialog = null;
        }
    }
}