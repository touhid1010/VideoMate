package biz.vumobile.videomate.login;

import biz.vumobile.videomate.model.user.Userinfo;

/**
 * Created by IT-10 on 1/17/2018.
 */

/**
 * will return Userinfo singleton
 */
public class UserSingleton {

    private static Userinfo userinfo;

    private UserSingleton() {

    }

    public static Userinfo getInstanceOfUserModel() {
        if (userinfo == null) {
            userinfo = new Userinfo();
        }
        return userinfo;
    }


}
