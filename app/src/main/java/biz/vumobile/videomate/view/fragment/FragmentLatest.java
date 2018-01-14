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
import biz.vumobile.videomate.adapter.ContentTestAdapter;
import biz.vumobile.videomate.model.receivedata.TestClass;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
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

    private SwipeRefreshLayout swipeRefreshLayout;

    private ApiInterface apiInterface;
    private Call<List<TestClass>> callContent;
    private List<TestClass> testClasses = new ArrayList<>();
    private TestClass testClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_layout, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh_latest);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = view.findViewById(R.id.recycler_view_test_content_latest);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new ContentTestAdapter(getActivity(),testClasses);
        mRecyclerView.setAdapter(adapter);

        apiInterface = RetrofitClient.getRetrofitClient(ApiInterface.BASE_URL).create(ApiInterface.class);

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
                Log.d("Click","Clickable");
                startActivity(new Intent(getActivity(), VideoViewActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void parseContent(ApiInterface apiInterface) {

        swipeRefreshLayout.setRefreshing(true);

        testClasses.clear();

        callContent = apiInterface.getContents();

        callContent.enqueue(new Callback<List<TestClass>>() {
            @Override
            public void onResponse(Call<List<TestClass>> call, Response<List<TestClass>> response) {

                Log.d("Response",response.body().toString());

                swipeRefreshLayout.setRefreshing(false);

                testClasses.addAll(response.body());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TestClass>> call, Throwable t) {
                Log.d("Response",t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        parseContent(apiInterface);
    }
}
