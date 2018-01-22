package biz.vumobile.videomate.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.login.MyLoginOperation;
import biz.vumobile.videomate.model.receivedata.Comment;
import biz.vumobile.videomate.model.receivedata.CommentsClass;
import biz.vumobile.videomate.model.senddata.LikeClass;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyConstraints;
import biz.vumobile.videomate.view.fragment.FragmentCommentViews;
import biz.vumobile.videomate.view.fragment.FragmentMe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static biz.vumobile.videomate.utils.MyConstraints.FRAGMENT_TAG_ME;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private int comment_count;
    private Fragment fragmentMe;
    private ImageView imgUserImage;
    private Intent shareIntent;
    private Intent intent;
    private TextView txtLikeCount, txtCommentCount, txtViews;
    private FragmentManager fragmentManager;
    private VideoView videoView;
    private ImageView imgClose, imgComment, imgLoading, imgShare, imglike, imgFollow;
    private ApiInterface apiInterface;
    private Call<LikeClass> likeClassCall;
    private Call<CommentsClass> commentClassCall;
    private List<Comment> commentsClassList = new ArrayList<>();
    private Uri uri;
    public static int like_count, view_count;
    public static String video_id, opponentId;
    private String videoUrl;// = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D480x320/2018_1.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        initUI();

        Glide.with(getApplicationContext()).load(R.drawable.loading).into(imgLoading);

        fragmentMe = new FragmentMe();
        fragmentManager = getSupportFragmentManager();

        uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);

        getAllCommentsCount(video_id);

        setView(video_id);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);

                if (mediaPlayer.isPlaying()){
                    imgLoading.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setView(String video_id) {
        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);
        likeClassCall = apiInterface.setView(video_id);

        likeClassCall.enqueue(new Callback<LikeClass>() {
            @Override
            public void onResponse(Call<LikeClass> call, Response<LikeClass> response) {
                Log.d("Response","Viewed "+response.body().getResult());
                view_count+=1;
                int views = view_count;
                txtViews.setText(String.valueOf(views));
            }

            @Override
            public void onFailure(Call<LikeClass> call, Throwable t) {
                Log.d("Response","Viewed "+t.getMessage());
            }
        });
    }

    private void initUI() {

        // this used for hide top status bar
        getWindow().clearFlags(WindowManager
                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgFollow = findViewById(R.id.imgFollow);
        txtViews = findViewById(R.id.txtViews);
        txtCommentCount = findViewById(R.id.txtCommentCount);
        txtLikeCount = findViewById(R.id.txtLikeCount);
        imglike = findViewById(R.id.imglikee);
        intent = getIntent();

        videoUrl = intent.getStringExtra("video_url");

        txtLikeCount = findViewById(R.id.txtLikeCount);
        imgUserImage = findViewById(R.id.imgUserImage);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgLoading = (ImageView) findViewById(R.id.imgLoading);
        videoView = (VideoView) findViewById(R.id.videoView);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgComment = (ImageView) findViewById(R.id.imgComment);

        imgFollow.setOnClickListener(this);
        imglike.setOnClickListener(this);
        imgUserImage.setOnClickListener(this);
        imgComment.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        imgShare.setOnClickListener(this);

        txtViews.setText(String.valueOf(view_count));
        txtLikeCount.setText(String.valueOf(like_count));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imgClose:
                if (!videoView.isPlaying()){
                    finish();
                }else {
                    videoView.stopPlayback();
                    finish();
                }
                break;
            case R.id.imgComment:
                openFragment(new FragmentCommentViews());
                break;
            case R.id.imgShare:
                shareContent();
                break;
            case R.id.imgUserImage:
                // go to user profile
                addFragment(R.id.framelayoutProfile, fragmentMe, FRAGMENT_TAG_ME);
                break;
            case R.id.imglikee:
                like_count+=1;
                txtLikeCount.setText(String.valueOf(like_count));
                giveLike(video_id);
                break;
            case R.id.imgFollow:
                followUser(MyLoginOperation.getInstance(this).getUserId(), opponentId);
                break;
        }
    }

    private void followUser(String userId, String opponentId) {

        HashMap<String, String> map = new HashMap<>();
        map.put("UserId",userId);
        map.put("FollowedUserId",opponentId);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);
        likeClassCall = apiInterface.followUser(map);

        likeClassCall.enqueue(new Callback<LikeClass>() {
            @Override
            public void onResponse(Call<LikeClass> call, Response<LikeClass> response) {
                Log.d("Response","Follow "+response.body().getResult());
                Toast.makeText(getApplicationContext(), response.body().getResult(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LikeClass> call, Throwable t) {
                Log.d("Response","Follow "+t.getMessage());
            }
        });
    }

    private void giveLike(String video_id) {

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        likeClassCall = apiInterface.giveLike(video_id);

        likeClassCall.enqueue(new Callback<LikeClass>() {
            @Override
            public void onResponse(Call<LikeClass> call, Response<LikeClass> response) {
                Log.d("ResponseLike",response.body().getResult());
            }

            @Override
            public void onFailure(Call<LikeClass> call, Throwable t) {
                Log.d("ResponseLike",t.getMessage());
            }
        });

    }

    private void addFragment(@IdRes int containerViewId,
                             @NonNull Fragment fragment,
                             @NonNull String fragmentTag) {
        Fragment fragmentA = fragmentManager.findFragmentByTag(FRAGMENT_TAG_ME);
        if (fragmentA == null) {
            fragmentManager
                    .beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top)
                    .add(containerViewId, fragment, fragmentTag)
                    .disallowAddToBackStack()
                    .commit();
        }
    }

    private void shareContent() {
        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, videoUrl);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    @Override
    protected void onPause() {
        videoView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    public void openFragment(final Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.commentVideoView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void getAllCommentsCount(String video_id){
        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);
        commentClassCall = apiInterface.getComments(video_id);

        commentClassCall.enqueue(new Callback<CommentsClass>() {
            @Override
            public void onResponse(Call<CommentsClass> call, Response<CommentsClass> response) {
                commentsClassList.addAll(response.body().getComments());
                txtCommentCount.setText(String.valueOf(commentsClassList.size()));
            }

            @Override
            public void onFailure(Call<CommentsClass> call, Throwable t) {

            }
        });
    }
}
