package biz.vumobile.videomate.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.MeMenuRecyclerAdapter;
import biz.vumobile.videomate.login.UserSingleton;
import biz.vumobile.videomate.model.user.MeMenuModel;
import biz.vumobile.videomate.model.user.Userinfo;
import biz.vumobile.videomate.utils.SimpleDividerItemDecoration;

/**
 * Created by IT-10 on 1/16/2018.
 */

public class FragmentMe extends Fragment implements View.OnClickListener {

    RecyclerView recyclerViewMeMenu;
    List<MeMenuModel> meMenuModels = new ArrayList<>();
    MeMenuRecyclerAdapter meMenuRecyclerAdapter;
    LinearLayout linearLayoutVideoTab, linearLayoutFollowingTab, linearLayoutFollowerTab;

    ImageView imageViewProfilePic;
    TextView textViewUserName, textViewUserId, textViewVideoCount, textViewFollowingCount, textViewFollowerCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewProfilePic = view.findViewById(R.id.imageViewProfilePic);
        textViewUserName = view.findViewById(R.id.textViewUserName);
        textViewUserId = view.findViewById(R.id.textViewUserId);
        textViewVideoCount = view.findViewById(R.id.textViewVideoCount);
        textViewFollowingCount = view.findViewById(R.id.textViewFollowingCount);
        textViewFollowerCount = view.findViewById(R.id.textViewFollowerCount);

        linearLayoutVideoTab = view.findViewById(R.id.linearLayoutVideoTab);
        linearLayoutFollowingTab = view.findViewById(R.id.linearLayoutFollowingTab);
        linearLayoutFollowerTab = view.findViewById(R.id.linearLayoutFollowerTab);
        linearLayoutVideoTab.setOnClickListener(this);
        linearLayoutFollowingTab.setOnClickListener(this);
        linearLayoutFollowerTab.setOnClickListener(this);

        recyclerViewMeMenu = view.findViewById(R.id.recyclerViewMeMenu);
        meMenuRecyclerAdapter = new MeMenuRecyclerAdapter(getActivity(), meMenuModels);
        recyclerViewMeMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMeMenu.setAdapter(meMenuRecyclerAdapter);

        recyclerViewMeMenu.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        meMenuRecyclerAdapter.setItemClickListener(new MeMenuRecyclerAdapter.ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getActivity(), "" + meMenuModels.get(position).getMenuName(), Toast.LENGTH_SHORT).show();
            }
        });

        setUserInfoToUI();

        loadListData();

    }

    private void setUserInfoToUI() {
        Userinfo userinfo = UserSingleton.getInstanceOfUserModel();

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
        MeMenuModel meMenuModel3 = new MeMenuModel(R.drawable.ic_action_info, "Logout");
        meMenuModels.add(meMenuModel);
        meMenuModels.add(meMenuModel2);
        meMenuModels.add(meMenuModel3);

        meMenuRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayoutVideoTab:

                break;

            case R.id.linearLayoutFollowingTab:

                break;

            case R.id.linearLayoutFollowerTab:

                break;
        }
    }

    

}
