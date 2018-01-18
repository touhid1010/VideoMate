package biz.vumobile.videomate.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.login.MyLoginOperation;
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

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private ApiInterface apiInterface;
    private Call<NewUserRegisterResult> newUserRegisterResultCall;
    private Call<UserModel> userModelCall;

    ImageView imageViewAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        imageViewAnimation = findViewById(R.id.imageViewAnimation);


        /**
         * User register logic:
         *
         * Check SP if exists get user data by id from api
         * if not exists register guest user and get user data by id which comes after register response
         *
         */

        String uId = MyLoginOperation.getInstance(this).getUserId();
        if (uId.equals("")) {
            // Register a guest user
            registerUserRequest();
        } else {
            // Get user data from saved id
            getUserDataForSingleton(uId);
        }


    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();

        Glide.with(this)
                .load(R.raw.video_animation)
                .into(new GlideDrawableImageViewTarget((ImageView) findViewById(R.id.imageViewAnimation)));


    }

    public void getUserDataForSingleton(String uId) {

        userModelCall = apiInterface.getUserById(uId);

        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d(TAG, "onResponse: " + response.body());

                if (response.body().getUserinfo() == null) {
                    return;
                }

                if (response.body().getUserinfo().size() < 1){
                    registerUserRequest();
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

                getUserDataForSingleton(String.valueOf(response.body().getId()));

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
