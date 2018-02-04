package biz.vumobile.videomate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import biz.vumobile.videomate.view.fragment.MyVideoFragment;

public class VideoFollowingFollowerActivity extends AppCompatActivity {

//    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_following_follower);

//        frameLayout = findViewById(R.id.frameLayout);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new MyVideoFragment()).commit();



    }



}
