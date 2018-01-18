package biz.vumobile.videomate.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.MeMenuRecyclerAdapter;
import biz.vumobile.videomate.model.user.MeMenuModel;
import biz.vumobile.videomate.model.user.Userinfo;
import biz.vumobile.videomate.utils.MyApplication;
import biz.vumobile.videomate.utils.SimpleDividerItemDecoration;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by IT-10 on 1/16/2018.
 */

public class FragmentMe extends Fragment implements View.OnClickListener, MeMenuRecyclerAdapter.ItemClickListener {

    RecyclerView recyclerViewMeMenu;
    List<MeMenuModel> meMenuModels = new ArrayList<>();
    MeMenuRecyclerAdapter meMenuRecyclerAdapter;
    LinearLayout linearLayoutVideoTab, linearLayoutFollowingTab, linearLayoutFollowerTab;

    ImageButton imageButtonFacebookConnect;
    ImageView imageViewProfilePic;
    TextView textViewUserName, textViewUserId, textViewVideoCount, textViewFollowingCount, textViewFollowerCount;

    CallbackManager callbackManager;
    LoginButton loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageButtonFacebookConnect = view.findViewById(R.id.imageButtonFacebookConnect);
        imageViewProfilePic = view.findViewById(R.id.imageViewProfilePic);
        textViewUserName = view.findViewById(R.id.textViewUserName);
        textViewUserId = view.findViewById(R.id.textViewUserId);
        textViewVideoCount = view.findViewById(R.id.textViewVideoCount);
        textViewFollowingCount = view.findViewById(R.id.textViewFollowingCount);
        textViewFollowerCount = view.findViewById(R.id.textViewFollowerCount);

        linearLayoutVideoTab = view.findViewById(R.id.linearLayoutVideoTab);
        linearLayoutFollowingTab = view.findViewById(R.id.linearLayoutFollowingTab);
        linearLayoutFollowerTab = view.findViewById(R.id.linearLayoutFollowerTab);
        imageButtonFacebookConnect.setOnClickListener(this);
        linearLayoutVideoTab.setOnClickListener(this);
        linearLayoutFollowingTab.setOnClickListener(this);
        linearLayoutFollowerTab.setOnClickListener(this);

        recyclerViewMeMenu = view.findViewById(R.id.recyclerViewMeMenu);
        meMenuRecyclerAdapter = new MeMenuRecyclerAdapter(getActivity(), meMenuModels);
        recyclerViewMeMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMeMenu.setAdapter(meMenuRecyclerAdapter);

        recyclerViewMeMenu.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        meMenuRecyclerAdapter.setItemClickListener(this);

        setUserInfoToUI();

        loadListData();


        callbackManager = CallbackManager.Factory.create();
        loginButton = view.findViewById(R.id.login_button);
        facebookLoginUiAndOperation();


    }

    private void setUserInfoToUI() {
        Userinfo userinfo = MyApplication.getInstanceOfUserModel();

//        imageViewProfilePic = view.findViewById(R.id.imageViewProfilePic);
        Glide.with(getActivity()).load(userinfo.getEmail()).placeholder(R.drawable.user).into(imageViewProfilePic);
        textViewUserName.setText(userinfo.getName());
        textViewUserId.setText("ID: " + String.valueOf(userinfo.getID()));
        textViewVideoCount.setText(String.valueOf(userinfo.getTotalUploadedVideo()));
        textViewFollowingCount.setText(String.valueOf(userinfo.getFollowing()));
        textViewFollowerCount.setText(String.valueOf(userinfo.getFollowers()));
    }

    private void loadListData() {
        meMenuModels.clear();

        MeMenuModel meMenuModel = new MeMenuModel(R.drawable.ic_action_thumb_up, "Like us in facebook");
        MeMenuModel meMenuModel2 = new MeMenuModel(R.drawable.ic_action_feedback, "Feedback");
        //   MeMenuModel meMenuModel3 = new MeMenuModel(R.drawable.ic_action_info, "Logout");
        meMenuModels.add(meMenuModel);
        meMenuModels.add(meMenuModel2);
        //     meMenuModels.add(meMenuModel3);

        meMenuRecyclerAdapter.notifyDataSetChanged();

    }

    @Override // tab or button
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayoutVideoTab:
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.linearLayoutFollowingTab:
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.linearLayoutFollowerTab:
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageButtonFacebookConnect:
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override // recyclerView
    public void onClick(View v, int position) {
        switch (position) {
            case 0:
                startActivity(getOpenFacebookIntent(getActivity()));
                break;

            case 1:
                sendEmailFeedback(getActivity());
                break;

            case 2:

                break;
        }

    }


    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/167500539949438"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/vumobileltd/"));
        }
    }

    public static void sendEmailFeedback(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "touhidul.islam@vumobile.biz", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Video Mate Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear concern, ");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void facebookLoginUiAndOperation() {

        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
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
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
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


}
