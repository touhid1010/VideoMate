package biz.vumobile.videomate.view.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.utils.BroadcastDataPass;

/**
 * Created by toukirul on 10/1/2018.
 */

public class FragmentVideoPlayList extends Fragment {

    MediaPlayer.OnPreparedListener mPreparedListener;

    private String videoUrl = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D480x320/2018_1.mp4";

    private ImageView imgClose;
    private SurfaceView surfaceView;
    private MediaPlayer mp;
    private SurfaceHolder surfaceHolder;
    private ImageView imgLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_play, container, false);

        imgLoading = (ImageView) view.findViewById(R.id.imgLoading);
        // Glide.with(this).load(R.drawable.loading).into(imgLoading);

        imgClose = (ImageView) view.findViewById(R.id.imgClose);
        surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Log.d("StateListener", "S" + BroadcastDataPass.getSate());

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Close", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        playVideo();
    }

    private void playVideo() {

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mp.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        try {
            mp = new MediaPlayer();
            mp.setOnPreparedListener(mPreparedListener);
            mp.setDataSource(videoUrl);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }



        mPreparedListener = new MediaPlayer.OnPreparedListener()
        {
            /** Called when MediaPlayer is ready */
            public void onPrepared(MediaPlayer player)
            {
                mp.start();
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("PauseCalled", "PauseCalled");
        if (mp.isPlaying()) {
            mp.pause();
        }
    }
}