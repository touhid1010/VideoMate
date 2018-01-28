package biz.vumobile.videomate.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.login.MyLoginOperation;
import biz.vumobile.videomate.model.user.UpdateUserResult;
import biz.vumobile.videomate.model.user.UserModel;
import biz.vumobile.videomate.model.user.Userinfo;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyApplication;
import biz.vumobile.videomate.utils.MyConstraints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    CallbackManager callbackManager;
    LoginButton loginButton;
    private ApiInterface apiInterface;
    private Call<UpdateUserResult> userModelCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        facebookLoginUiAndOperation();

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

    }

    private void facebookLoginUiAndOperation() {
        loginButton.setReadPermissions("public_profile");
        // If using in a fragment
//        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + resultCode + data);

    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json_object, GraphResponse response) {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("userProfile", json_object.toString());
//                        startActivity(intent);

                        updateLoginInfoWithFacebookData(json_object.toString());

                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }

    private void updateLoginInfoWithFacebookData(String jsondata) {
        JSONObject response;
        try {
            response = new JSONObject(jsondata);
            String name = response.get("name").toString();

       //     String email = response.get("email").toString();

            JSONObject profile_pic_data = new JSONObject(response.get("picture").toString());
            JSONObject profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            String url = profile_pic_url.getString("url");

            Userinfo userinfo = new Userinfo();
            userinfo.setName(name);
            userinfo.setID(Integer.valueOf(MyLoginOperation.getInstance(this).getUserId()));
            userinfo.setFblogin(1);
         //   userinfo.setEmail(email);
            userinfo.setImageUrl(url);


            getUserDataForSingleton(userinfo);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserDataForSingleton(final Userinfo userinfo) {

        userModelCall = apiInterface.updateUserWithFbInfo(userinfo);

        userModelCall.enqueue(new Callback<UpdateUserResult>() {
            @Override
            public void onResponse(Call<UpdateUserResult> call, Response<UpdateUserResult> response) {
                Log.d(TAG, "onResponse: " + response.body());

                if (response.body().getResult() == null) {
                    return;
                }

                if (response.body().getResult().equals("success")) {
                    Toast.makeText(LoginActivity.this, "FB login success", Toast.LENGTH_SHORT).show();

                    // set data to singleton to ensure upload at first time
                    MyApplication.getInstanceOfUserModel().setImageUrl(userinfo.getImageUrl());
                    MyApplication.getInstanceOfUserModel().setName(userinfo.getName());
                    MyApplication.getInstanceOfUserModel().setFblogin(userinfo.getFblogin());


                    finish();
                }

            }

            @Override
            public void onFailure(Call<UpdateUserResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "Failed, Try again.", Toast.LENGTH_SHORT).show();
            }
        });


    }




}
