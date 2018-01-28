package biz.vumobile.videomate.view.fragment;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import biz.vumobile.videomate.R;

import static android.content.ContentValues.TAG;

/**
 * Created by IT-10 on 1/22/2018.
 */

public class VideoPlayerFragment extends Fragment implements TextureView.SurfaceTextureListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener {

    ViewPager pager;
    private MediaPlayer mMediaPlayer;
    private TextureView mPreview;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_player, container, false);

        pager = (ViewPager) container;
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
                if (VideoPlayerFragment.this.isVisible() && state == 0) {
                    playVideo();
                } else {
                    pauseVideo();
                }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPreview = view.findViewById(R.id.textureView);
        mPreview.setSurfaceTextureListener(this);
    }

    public void playVideo() {
        if (this.mMediaPlayer != null) {
            try {
                this.mMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void pauseVideo() {
        if (this.mMediaPlayer != null) {
            try {
                if (this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.seekTo(0);
                    this.mMediaPlayer.pause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface s = new Surface(surface);

        String vidUrl = getArguments().getString("key");
        Log.d(TAG, "onSurfaceTextureAvailable: " + vidUrl);

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(vidUrl);
            mMediaPlayer.setSurface(s);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }
}
