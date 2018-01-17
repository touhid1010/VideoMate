package biz.vumobile.videomate.login;

import android.content.Context;
import android.content.SharedPreferences;

import static biz.vumobile.videomate.utils.MyConstraints.SHAR_PREF_NAME_USER;
import static biz.vumobile.videomate.utils.MyConstraints.SHAR_PREF_USER_ID;

/**
 * Created by IT-10 on 1/17/2018.
 */

public class MyLoginOperation {

    private static SharedPreferences sharedPreferences;
    private static MyLoginOperation myLoginOperation;

    public static MyLoginOperation getInstance(Context context) {
        if (myLoginOperation == null) {
            sharedPreferences = context.getSharedPreferences(SHAR_PREF_NAME_USER, Context.MODE_PRIVATE);
            myLoginOperation = new MyLoginOperation();
        }
        return myLoginOperation;
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(SHAR_PREF_USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(SHAR_PREF_USER_ID, "");
    }


    public void logout() {
        sharedPreferences.edit().clear().apply();
    }

}
