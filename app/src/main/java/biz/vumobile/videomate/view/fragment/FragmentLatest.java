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

/**
 * Created by IT-10 on 1/14/2018.
 */

public class FragmentLatest extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Intent intent;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ApiInterface apiInterface;
    private Call<GetAllPostsClass> callLatest;
    private List<Video> latestListVideo = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_layout, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh_latest);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = view.findViewById(R.id.recycler_view_test_content_latest);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new AdapterGetAllPosts(getActivity(), latestListVideo);
        mRecyclerView.setAdapter(adapter);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                parseContent(apiInterface);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Click", "Clickable");

                Video posts = latestListVideo.get(position);

                VideoViewActivity.like_count = posts.getLike();
                VideoViewActivity.video_id = String.valueOf(posts.getVideoId());
                VideoViewActivity.view_count = posts.getView();

                intent = new Intent(getActivity(), VideoViewActivity.class);
//                intent.putExtra("video_url", posts.getVideoUrl());
                intent.putExtra("position", position);
                VideoViewActivity.videoList.clear();
                for (int a = 0; a < latestListVideo.size(); a++) {
                    VideoViewActivity.videoList.addAll(latestListVideo);
                }
                startActivity(intent);
                //startActivity(new Intent(getActivity(), VideoViewActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void parseContent(ApiInterface apiInterface) {
        swipeRefreshLayout.setRefreshing(true);
        latestListVideo.clear();
        callLatest = apiInterface.getPostsLatest(MyLoginOperation.getInstance(getActivity()).getUserId());
        callLatest.enqueue(new Callback<GetAllPostsClass>() {
            @Override
            public void onResponse(Call<GetAllPostsClass> call, Response<GetAllPostsClass> response) {
                swipeRefreshLayout.setRefreshing(false);
                latestListVideo.addAll(response.body().getVideos());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetAllPostsClass> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        callContent.enqueue(new Callback<List<TestClass>>() {
//            @Override
//            public void onResponse(Call<List<TestClass>> call, Response<List<TestClass>> response) {
//
//                Log.d("Response",response.body().toString());
//
//                swipeRefreshLayout.setRefreshing(false);
//
//                testClasses.addAll(response.body());
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<TestClass>> call, Throwable t) {
//                Log.d("Response",t.getMessage());
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void onRefresh() {
        parseContent(apiInterface);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
