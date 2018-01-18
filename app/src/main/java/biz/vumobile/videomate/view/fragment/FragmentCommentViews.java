package biz.vumobile.videomate.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.CommentsAdapter;
import biz.vumobile.videomate.model.receivedata.CommentsClass;
import biz.vumobile.videomate.model.senddata.LikeClass;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyConstraints;
import biz.vumobile.videomate.view.activity.VideoViewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by toukirul on 11/1/2018.
 */

public class FragmentCommentViews extends Fragment {

    private RecyclerView recyclerComments;
    private ImageView imgClose, imgSend;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mManager;
    private EditText etComment;
    private Call<LikeClass> likeClassCall;
    private ApiInterface apiInterface;
    private List<CommentsClass> commentsClassList = new ArrayList<>();
    private CommentsClass commentsClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_views, container, false);

        imgSend = view.findViewById(R.id.imgSend);
        imgClose = view.findViewById(R.id.imgClose);
        etComment = view.findViewById(R.id.etComment);
        adapter = new CommentsAdapter(getActivity(), commentsClassList);
        recyclerComments = view.findViewById(R.id.recyclerComments);
        mManager = new LinearLayoutManager(getActivity());
        recyclerComments.setLayoutManager(mManager);
        recyclerComments.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etComment.getText().equals("")){
                    hideSoftKeyBoard();
                    sendComments(etComment.getText().toString(), VideoViewActivity.video_id);
                }
                etComment.setText("");
            }
        });

        etComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEND){
                    hideSoftKeyBoard();
                    sendComments(etComment.getText().toString(),VideoViewActivity.video_id);
                    return true;
                }
                etComment.setText("");
                return false;
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void hideSoftKeyBoard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void sendComments(String comment, String video_id) {
        Log.d("Comments","method call");

        hitCommentApi(comment, video_id);

    }

    private void hitCommentApi(final String comment, String video_id) {

        HashMap<String, String> map = new HashMap<>();
        map.put("VideoId",video_id);
        map.put("Comment", comment);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);
        likeClassCall = apiInterface.giveComment(map);

        likeClassCall.enqueue(new Callback<LikeClass>() {
            @Override
            public void onResponse(Call<LikeClass> call, Response<LikeClass> response) {

                Log.d("CommentStatus", response.body().getResult());

                commentsClass = new CommentsClass();
                commentsClass.setDate_time("12 hours ago");
                commentsClass.setUser_comment(comment);
                commentsClass.setUser_name("User Name-!");

                commentsClassList.add(commentsClass);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LikeClass> call, Throwable t) {

            }
        });
    }
}
