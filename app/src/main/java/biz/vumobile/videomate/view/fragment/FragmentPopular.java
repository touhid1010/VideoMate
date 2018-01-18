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

public class FragmentPopular extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private AdapterGetAllPosts adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Intent intent;

    private SwipeRefreshLayout swipeRefreshLayout;

    private retrofit2.Call<GetAllPostsClass> getAllPostsClassCall;
    private List<Video> resultList = new ArrayList<>();
    private ApiInterface apiInterface;
    private Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popular_layout, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = view.findViewById(R.id.recycler_view_test_content);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new AdapterGetAllPosts(getActivity(), resultList);
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

                parseContent();
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Click", "Clickable");

                Video posts = resultList.get(position);

                VideoViewActivity.like_count = posts.getLike();
                VideoViewActivity.video_id = String.valueOf(posts.getVideoId());
                VideoViewActivity.view_count = posts.getView();
                VideoViewActivity.opponentId = posts.getUser().getUserID();

                intent = new Intent(getActivity(), VideoViewActivity.class);
                intent.putExtra("video_url", posts.getVideoUrl());
                startActivity(intent);
                //startActivity(new Intent(getActivity(), VideoViewActivity.class));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void parseContent() {

        //swipeRefreshLayout.setRefreshing(true);

        resultList.clear();

        getAllPostsClassCall = apiInterface.getPosts();

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

//        allPostsClassCall = apiInterface.getPosts();
//
//        allPostsClassCall.enqueue(new Callback<GetAllPostsClass>() {
//            @Override
//            public void onResponse(Call<GetAllPostsClass> call, Response<GetAllPostsClass> response) {
//                GetAllPostsClass getAllPostsClass = response.body();
//                getAllPostsClass.setResult(response.body().getResult());
//
//                resultList.addAll(getAllPostsClass.getResult());
//
//                adapter.notifyDataSetChanged();
////                for (int i = 0; i<getAllPostsClass.getResult().size(); i++){
////                    Log.d("Responsee",getAllPostsClass.getResult().get(i).getThumbnailUrl());
////                    result = new Result();
////                    result.setDescription(getAllPostsClass.getResult().get(i).getDescription());
////                    result.setThumbnailUrl(getAllPostsClass.getResult().get(i).getThumbnailUrl());
////
////                    resultList.add(result);
////                    adapter.notifyDataSetChanged();
////                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllPostsClass> call, Throwable t) {
//
//            }
//        });

    }

    @Override
    public void onRefresh() {

        parseContent();
    }
}
