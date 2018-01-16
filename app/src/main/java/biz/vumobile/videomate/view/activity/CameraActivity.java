package biz.vumobile.videomate.view.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.WhiteBalance;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.utils.MyConstraints;

import static biz.vumobile.videomate.utils.MyConstraints.VIDEO_RECORD_TIME;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    CameraView cameraView;
    ImageButton imageButtonRecord, imageButtonSwitchCamera, imageButtonCameraClose;

    ImageView imageViewRecAnim;
    TextView textViewRecordDuration;

    long recTimeToShow = 0;
    boolean runLoop = false;

    private ProgressBar progressBarTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initUI();

        // To show record time
        showVideoDuration();

    }

    private void initUI() {

        progressBarTimer = findViewById(R.id.progressBarTimer);

        cameraView = findViewById(R.id.cameraView);
        cameraView.setSessionType(SessionType.VIDEO);

//        imageViewRecAnim = findViewById(R.id.imageViewRecAnim);
        textViewRecordDuration = findViewById(R.id.textViewRecordDuration);
        imageButtonRecord = findViewById(R.id.imageButtonRecord);
        imageButtonSwitchCamera = findViewById(R.id.imageButtonSwitchCamera);
        imageButtonCameraClose = findViewById(R.id.imageButtonCameraClose);
        imageButtonRecord.setOnClickListener(this);
        imageButtonSwitchCamera.setOnClickListener(this);
        imageButtonCameraClose.setOnClickListener(this);

        cameraView.setWhiteBalance(WhiteBalance.AUTO);
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(CameraOptions options) {
                super.onCameraOpened(options);
                // Toast.makeText(getActivity(), "Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);
                imageButtonRecord.setEnabled(true);
            }

            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);

                hideVideoDuration();
                goToEditVideo(video.getPath());
            }

            @Override
            public void onFocusStart(PointF point) {
                super.onFocusStart(point);
                Toast.makeText(getApplicationContext(), "Focus start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFocusEnd(boolean successful, PointF point) {
                super.onFocusEnd(successful, point);
                Toast.makeText(getApplicationContext(), "Focus end", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonRecord:
                String timeName = String.valueOf(System.currentTimeMillis()); // getStorageDir(getActivity(), timeName+".mp4")
                File file = new File("/storage/emulated/0/VideoMate/");
                file.mkdir();

                cameraView.startCapturingVideo(new File(file, timeName + ".mp4"), VIDEO_RECORD_TIME);
                imageButtonRecord.setEnabled(false);

                runLoop = true;

                break;

            case R.id.imageButtonSwitchCamera:
                if (cameraView.getFacing() == Facing.FRONT) {
                    cameraView.setFacing(Facing.BACK);
                    imageButtonSwitchCamera.setImageResource(R.drawable.ic_action_camera_front);
                } else {
                    cameraView.setFacing(Facing.FRONT);
                    imageButtonSwitchCamera.setImageResource(R.drawable.ic_action_camera_rear);
                }
                break;

            case R.id.imageButtonCameraClose:
                onBackPressed();
                break;

        }
    }

    public void goToEditVideo(String videoPath) {
        imageButtonRecord.setEnabled(true);

        Intent intent = new Intent(this, VideoEditUploadActivity.class);
        intent.putExtra(MyConstraints.VIDEO_PATH, videoPath);
        startActivity(intent);

        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideVideoDuration();
        cameraView.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }

    public void hideVideoDuration() {
        runLoop = false;
    }

    public void showVideoDuration() {
        // Make runLoop true of false to show record duration
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!runLoop) {
                    recTimeToShow = 0;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (textViewRecordDuration != null) {
                                textViewRecordDuration.setText("");
                            }

                        }
                    });
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (MyConstraints.VIDEO_RECORD_TIME / 1000 < recTimeToShow) {
                            return;
                        }
                        if (textViewRecordDuration != null) {
                            progressBarTimer.setProgress((int) recTimeToShow);
                            textViewRecordDuration.setText(String.valueOf(recTimeToShow) + " sec");
                        }
                    }
                });

                recTimeToShow++;
            }
        }, 0, 1000);
    }

}
