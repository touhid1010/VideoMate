package biz.vumobile.videomate.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.view.fragment.FragmentCommentViews;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent shareIntent;
    private VideoView videoView;
    private ImageView imgClose, imgComment, imgLoading, imgShare;
    private Uri uri;
    private String videoUrl = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D480x320/2018_1.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        initUI();

        Glide.with(getApplicationContext()).load(R.drawable.loading).into(imgLoading);

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
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgLoading = (ImageView) findViewById(R.id.imgLoading);
        videoView = (VideoView) findViewById(R.id.videoView);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgComment = (ImageView) findViewById(R.id.imgComment);

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
        super.onPause();

        videoView.stopPlayback();
    }

    public void openFragment(final Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.commentVideoView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
