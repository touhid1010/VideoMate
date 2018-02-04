package biz.vumobile.videomate.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import biz.vumobile.videomate.model.user.Userinfo;

/**
 * Created by IT-10 on 1/18/2018.
 */

public class MyApplication extends Application {

    private static Userinfo userinfo;

    public static Userinfo getInstanceOfUserModel() {
        if (userinfo == null) {
            userinfo = new Userinfo();
        }
        return userinfo;
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static int DEVICE_HEIGHT = 500;
    public static int getDeviceHeight() {
        return DEVICE_HEIGHT;
    }
    public static int setDeviceHeight(int height) {
        DEVICE_HEIGHT = height;
        return DEVICE_HEIGHT;
    }

}



