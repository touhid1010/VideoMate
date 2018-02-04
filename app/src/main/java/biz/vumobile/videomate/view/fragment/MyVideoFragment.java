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
 * Created by IT-10 on 1/30/2018.
 */

public class MyVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView recyclerViewMyVideo;
    private AdapterGetAllPosts adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Intent intent;

    private SwipeRefreshLayout swipeRefreshLayout;

    private retrofit2.Call<GetAllPostsClass> getAllPostsClassCall;
    private List<Video> resultList = new ArrayList<>();
    private ApiInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewMyVideo = view.findViewById(R.id.recyclerViewMyVideo);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewMyVideo.setLayoutManager(mLayoutManager);

        adapter = new AdapterGetAllPosts(getActivity(), resultList);
        recyclerViewMyVideo.setAdapter(adapter);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                parseContent();
            }
        });

        recyclerViewMyVideo.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewMyVideo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Click", "Clickable");

                Video posts = resultList.get(position);

                VideoViewActivity.like_count = posts.getLike();
                VideoViewActivity.video_id = String.valueOf(posts.getVideoId());
                VideoViewActivity.view_count = posts.getView();
                VideoViewActivity.opponentId = posts.getUser().getUserID();

                VideoViewActivity.video_url = posts.getVideoUrl();
                VideoViewActivity.user_photo = posts.getUser().getImageUrl().toString();
                VideoViewActivity.user_name = posts.getUser().getUserName();

                intent = new Intent(getActivity(), VideoViewActivity.class);
//                intent.putExtra("video_url", posts.getVideoUrl());

                intent.putExtra(VIDEO_POSITION, position);
                VideoViewActivity.videoList.clear();
                for (int a = 0; a < resultList.size(); a++) {
                    VideoViewActivity.videoList.addAll(resultList);
                }
                startActivity(intent);


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

        swipeRefreshLayout.setRefreshing(true);
        resultList.clear();
        adapter.notifyDataSetChanged();
        getAllPostsClassCall = apiInterface.getPostsOwn(MyLoginOperation.getInstance(getActivity()).getUserId());

        getAllPostsClassCall.enqueue(new Callback<GetAllPostsClass>() {
            @Override
            public void onResponse(Call<GetAllPostsClass> call, Response<GetAllPostsClass> response) {

                swipeRefreshLayout.setRefreshing(false);
                resultList.addAll(response.body().getVideos());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetAllPostsClass> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}
