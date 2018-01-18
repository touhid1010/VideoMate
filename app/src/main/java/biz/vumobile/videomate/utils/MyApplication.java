package biz.vumobile.videomate.utils;

import android.app.Application;

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

}



