package biz.vumobile.videomate.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.login.MyLoginOperation;
import biz.vumobile.videomate.login.UserSingleton;
import biz.vumobile.videomate.model.user.NewUserRegisterResult;
import biz.vumobile.videomate.model.user.UserModel;
import biz.vumobile.videomate.model.user.Userinfo;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyConstraints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private ApiInterface apiInterface;
    private Call<NewUserRegisterResult> newUserRegisterResultCall;
    private Call<UserModel> userModelCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        String uId = MyLoginOperation.getInstance(this).getUserId();
        if (uId.equals("")) {
            // Register a guest user
            registerUserRequest();
        } else {
            // Get user data from saved id
            getUserDataForSingleton(uId);
        }


    } // end of onCreate


    public void getUserDataForSingleton(String uId) {

        userModelCall = apiInterface.getUserById(uId);

        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d(TAG, "onResponse: " + response.body());

                Userinfo userinfo = response.body().getUserinfo().get(0);

                if (userinfo.getName().equals("")) {
                    userinfo.setName("User: " + userinfo.getID().toString());
                }

                UserSingleton.getInstanceOfUserModel().setName(userinfo.getName());
                UserSingleton.getInstanceOfUserModel().setEmail(userinfo.getEmail());
                UserSingleton.getInstanceOfUserModel().setPhone(userinfo.getPhone());
                UserSingleton.getInstanceOfUserModel().setFblogin(userinfo.getFblogin());
                UserSingleton.getInstanceOfUserModel().setAboutme(userinfo.getAboutme());
                UserSingleton.getInstanceOfUserModel().setFollowers(userinfo.getFollowers());
                UserSingleton.getInstanceOfUserModel().setFollowing(userinfo.getFollowing());
                UserSingleton.getInstanceOfUserModel().setPassword(userinfo.getPassword());
                UserSingleton.getInstanceOfUserModel().setTimeStamp(userinfo.getTimeStamp());
                UserSingleton.getInstanceOfUserModel().setImageUrl(userinfo.getImageUrl());
                UserSingleton.getInstanceOfUserModel().setGender(userinfo.getGender());
                UserSingleton.getInstanceOfUserModel().setID(userinfo.getID());
                UserSingleton.getInstanceOfUserModel().setTotalUploadedVideo(userinfo.getTotalUploadedVideo());

                startMainActivity();

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.printStackTrace();
                startMainActivity();
            }
        });


    }


    public void registerUserRequest() {

        Userinfo userinfo = new Userinfo();
        userinfo.setName("");
        userinfo.setPhone("");
        userinfo.setEmail("");
        userinfo.setFblogin(0);
        userinfo.setEmail("");

        newUserRegisterResultCall = apiInterface.registerUser(userinfo);

        newUserRegisterResultCall.enqueue(new Callback<NewUserRegisterResult>() {
            @Override
            public void onResponse(Call<NewUserRegisterResult> call, Response<NewUserRegisterResult> response) {
                Log.d(TAG, "onResponse: " + response.body());

                if (response.body().getResult().equals("Success")) {
                    MyLoginOperation.getInstance(getApplicationContext()).setUserId(response.body().getId().toString() + "");
                }

                startMainActivity();

            }

            @Override
            public void onFailure(Call<NewUserRegisterResult> call, Throwable t) {
                t.printStackTrace();

                startMainActivity();
            }
        });
    }

    public void startMainActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).start();

    }


}