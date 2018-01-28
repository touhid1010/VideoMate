package biz.vumobile.videomate.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.AdapterGetAllPosts;
import biz.vumobile.videomate.login.MyLoginOperation;
import biz.vumobile.videomate.model.receivedata.GetAllPostsClass;
import biz.vumobile.videomate.model.receivedata.Video;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyConstraints;
import biz.vumobile.videomate.utils.RecyclerTouchListener;
import biz.vumobile.videomate.view.activity.VideoViewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static biz.vumobile.videomate.utils.MyConstraints.VIDEO_POSITION;

/**
 * Created by toukirul on 16/1/2018.
 */

public class FollowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView textViewMessage;
    SwipeRefreshLayout swipeToRefreshFollow;
    RecyclerView recyclerViewFollow;

    private ApiInterface apiInterface;
    private Call<GetAllPostsClass> callLatest;
    private List<Video> followListVideo = new ArrayList<>();
    private Intent intent;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewMessage = view.findViewById(R.id.textViewMessage);
        textViewMessage.setVisibility(View.GONE);
        swipeToRefreshFollow = view.findViewById(R.id.swipeToRefreshFollow);
        swipeToRefreshFollow.setOnRefreshListener(this);
        recyclerViewFollow = view.findViewById(R.id.recyclerViewFollow);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewFollow.setLayoutManager(mLayoutManager);

        adapter = new AdapterGetAllPosts(getActivity(), followListVideo);
        recyclerViewFollow.setAdapter(adapter);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        swipeToRefreshFollow.post(new Runnable() {
            @Override
            public void run() {
                parseContent();
            }
        });

        recyclerViewFollow.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewFollow, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Click", "Clickable");

           //     Video posts = followListVideo.get(position);

//                VideoViewActivity.like_count = posts.getLike();
//                VideoViewActivity.video_id = String.valueOf(posts.getVideoId());
//                VideoViewActivity.view_count = posts.getView();
//                intent.putExtra("video_url", posts.getVideoUrl());

                VideoViewActivity.videoList.clear();
                for (int a = 0; a < followListVideo.size(); a++) {
                    VideoViewActivity.videoList.addAll(followListVideo);
                }

                intent = new Intent(getActivity(), VideoViewActivity.class);
                intent.putExtra(VIDEO_POSITION, position);
                startActivity(intent);
                //startActivity(new Intent(getActivity(), VideoViewActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onRefresh() {
        parseContent();
    }

    private void parseContent() {
        swipeToRefreshFollow.setRefreshing(true);
        followListVideo.clear();
        callLatest = apiInterface.getPostsFollowed(MyLoginOperation.getInstance(getActivity()).getUserId());
        callLatest.enqueue(new Callback<GetAllPostsClass>() {
            @Override
            public void onResponse(Call<GetAllPostsClass> call, Response<GetAllPostsClass> response) {
                swipeToRefreshFollow.setRefreshing(false);

                if (response.body() == null) {
                    textViewMessage.setVisibility(View.VISIBLE);
                    return;
                }

                if (response.body().getVideos().size() == 0) {
                    textViewMessage.setVisibility(View.VISIBLE);
                    return;
                }

                followListVideo.addAll(response.body().getVideos());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetAllPostsClass> call, Throwable t) {
                t.printStackTrace();
                swipeToRefreshFollow.setRefreshing(false);
            }

        });
    }


}
