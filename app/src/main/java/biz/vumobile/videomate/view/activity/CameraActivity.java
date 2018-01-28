package biz.vumobile.videomate.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.otaliastudios.cameraview.Audio;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.WhiteBalance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.Timer;
import java.util.TimerTask;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.utils.MyConstraints;
import biz.vumobile.videomate.view.fragment.AddAudioFragment;

import static biz.vumobile.videomate.utils.MyConstraints.FILE_PATH;
import static biz.vumobile.videomate.utils.MyConstraints.VIDEO_RECORD_TIME;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    CameraView cameraView;
    ImageButton imageButtonRecord, imageButtonSwitchCamera, imageButtonCameraClose, imageButtonMusic;

    ImageView imageViewRecAnim;
    TextView textViewRecordDuration;
    static ProgressDialog progressdialog;

    long recTimeToShow = 0;
    boolean runLoop = false;

    MediaPlayer mp;

    private ProgressBar progressBarTimer;
    private static String tempAudioPath = "";

    // MediaPlayer mediaPlayer;
    public void createProgressDialog() {

        progressdialog = new ProgressDialog(CameraActivity.this);

        progressdialog.setIndeterminate(false);

        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressdialog.setCancelable(true);

        progressdialog.setTitle("Adding audio...");

        progressdialog.setMax(100);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        createProgressDialog();
        initUI();
//        mediaPlayer = MediaPlayer.create(this, R.raw.iphonering);
        // To show record time
        showVideoDuration();


    }

    private void initUI() {

        progressBarTimer = findViewById(R.id.progressBarTimer);

        cameraView = findViewById(R.id.cameraView);
        cameraView.setSessionType(SessionType.VIDEO);

        mp = new MediaPlayer();

//        imageViewRecAnim = findViewById(R.id.imageViewRecAnim);
        textViewRecordDuration = findViewById(R.id.textViewRecordDuration);
        imageButtonRecord = findViewById(R.id.imageButtonRecord);
        imageButtonSwitchCamera = findViewById(R.id.imageButtonSwitchCamera);
        imageButtonCameraClose = findViewById(R.id.imageButtonCameraClose);
        imageButtonMusic = findViewById(R.id.imageButtonMusic);
        imageButtonRecord.setOnClickListener(this);
        imageButtonSwitchCamera.setOnClickListener(this);
        imageButtonCameraClose.setOnClickListener(this);
        imageButtonMusic.setOnClickListener(this);

        cameraView.setWhiteBalance(WhiteBalance.AUTO);
        cameraView.setAudio(Audio.OFF);
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
                File file = new File(FILE_PATH);
                file.mkdir();

                cameraView.startCapturingVideo(new File(file, timeName + ".mp4"), VIDEO_RECORD_TIME);
                imageButtonRecord.setEnabled(false);

                runLoop = true;

                // TODO

                if (mp != null) {
                    try {
                        mp.reset();
                        mp.setDataSource(tempAudioPath);
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                mediaPlayer.start();

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

            case R.id.imageButtonMusic:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top, R.anim.enter_from_bottom, R.anim.exit_to_top)
                        .add(R.id.frameLayoutAddAudio, new AddAudioFragment(), "AddAudioFragment")
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    public void goToEditVideo(String videoPath) {
        imageButtonRecord.setEnabled(true);

        Intent intent = new Intent(this, VideoEditUploadActivity.class);
        intent.putExtra(MyConstraints.VIDEO_PATH, videoPath);
        intent.putExtra(MyConstraints.AUDIO_PATH, tempAudioPath);
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

        tempAudioPath = "";
        try {
            mp.release();
            mp.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void fireFromAddAudioFragment(String url, String sName) {
        Log.d("t", "fireFromAddAudioFragment: " + url);
        new DownloadAudioFile().execute(url, sName);
    }

    int prog;

    public class DownloadAudioFile extends AsyncTask<String, Integer, String> { // Params, Progress, Result

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog.show();
        }

        @Override
        protected String doInBackground(String... urlAndName) {
            int count;
            try {
                URL url1 = new URL(urlAndName[0].trim().replaceAll(" ", "%20"));
                URLConnection connection = url1.openConnection();
                connection.connect();
                // This will be useful so that you can show a typical 0-100% progress bar
                int lengthOfFile = connection.getContentLength();

                // Download the file
                File file = new File(FILE_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }

                InputStream input = new BufferedInputStream(url1.openStream());
                String fileName = urlAndName[1];
                if (!fileName.contains(".mp4")) {
                    fileName = fileName + ".mp4";
                }
                OutputStream output = new FileOutputStream(FILE_PATH + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publishing the progress....
                    prog = (int) (total * 100 / lengthOfFile);
                    output.write(data, 0, count);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressdialog.setProgress(prog);
                        }
                    });
                }

                output.flush();
                output.close();
                input.close();


                // set file name to edit with video
                tempAudioPath = FILE_PATH + fileName;

            } catch (Exception e) {
                e.printStackTrace();



            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressdialog.dismiss();

        }
    }



}
