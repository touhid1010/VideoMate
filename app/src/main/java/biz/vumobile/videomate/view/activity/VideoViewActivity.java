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
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.view.fragment.FragmentCommentViews;
import biz.vumobile.videomate.view.fragment.FragmentMe;

import static biz.vumobile.videomate.utils.MyConstraints.FRAGMENT_TAG_ME;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment fragmentMe;
    private ImageView imgUserImage;
    private Intent shareIntent;
    private Intent intent;
    private FragmentManager fragmentManager;
    private VideoView videoView;
    private ImageView imgClose, imgComment, imgLoading, imgShare;
    private Uri uri;
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

    private void initUI() {

        // this used for hide top status bar
        getWindow().clearFlags(WindowManager
                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();
        videoUrl = intent.getStringExtra("video_url");
        imgUserImage = findViewById(R.id.imgUserImage);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgLoading = (ImageView) findViewById(R.id.imgLoading);
        videoView = (VideoView) findViewById(R.id.videoView);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgComment = (ImageView) findViewById(R.id.imgComment);

        imgUserImage.setOnClickListener(this);
        imgComment.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        imgShare.setOnClickListener(this);
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
        }
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
}
