package biz.vumobile.videomate.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import biz.vumobile.videomate.model.user.NewUserRegisterResult;
import biz.vumobile.videomate.model.user.UserModel;
import biz.vumobile.videomate.model.user.Userinfo;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyApplication;
import biz.vumobile.videomate.utils.MyConstraints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static biz.vumobile.videomate.utils.MyConstraints.SHAR_PREF_NAME_USER;
import static biz.vumobile.videomate.utils.MyConstraints.SHAR_PREF_USER_ID;

/**
 * Created by IT-10 on 1/17/2018.
 */

public class MyLoginOperation {

    private static final String TAG = "MyLoginOperation";
    private static SharedPreferences sharedPreferences;
    private static MyLoginOperation myLoginOperation;

    private ApiInterface apiInterface;
    private Call<UserModel> userModelCall;

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

    public void getUserDataForSingleton(String uId) {
        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        userModelCall = apiInterface.getUserById(uId);

        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d(TAG, "onResponse: " + response.body());

                if (response.body().getUserinfo() == null) {
                    return;
                }

                if (response.body().getUserinfo().size() < 1){
                    return;
                }
                Userinfo userinfo = response.body().getUserinfo().get(0);

                if (userinfo.getName().equals("")) {
                    userinfo.setName("User: " + userinfo.getID().toString());
                }

                MyApplication.getInstanceOfUserModel().setName(userinfo.getName());
                MyApplication.getInstanceOfUserModel().setEmail(userinfo.getEmail());
                MyApplication.getInstanceOfUserModel().setPhone(userinfo.getPhone());
                MyApplication.getInstanceOfUserModel().setFblogin(userinfo.getFblogin());
                MyApplication.getInstanceOfUserModel().setAboutme(userinfo.getAboutme());
                MyApplication.getInstanceOfUserModel().setFollowers(userinfo.getFollowers());
                MyApplication.getInstanceOfUserModel().setFollowing(userinfo.getFollowing());
                MyApplication.getInstanceOfUserModel().setPassword(userinfo.getPassword());
                MyApplication.getInstanceOfUserModel().setTimeStamp(userinfo.getTimeStamp());
                MyApplication.getInstanceOfUserModel().setImageUrl(userinfo.getImageUrl());
                MyApplication.getInstanceOfUserModel().setGender(userinfo.getGender());
                MyApplication.getInstanceOfUserModel().setID(userinfo.getID());
                MyApplication.getInstanceOfUserModel().setTotalUploadedVideo(userinfo.getTotalUploadedVideo());

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

}
