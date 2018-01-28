package biz.vumobile.videomate.view.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.coremedia.iso.boxes.Container;
import com.github.guilhe.circularprogressview.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.model.senddata.MyUploadPostResponseModel;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.ProgressRequestBody;
import biz.vumobile.videomate.utils.MyApplication;
import biz.vumobile.videomate.utils.MyConstraints;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static biz.vumobile.videomate.utils.MyConstraints.AUDIO_PATH;
import static biz.vumobile.videomate.utils.MyConstraints.FILE_PATH;
import static biz.vumobile.videomate.utils.MyConstraints.VIDEO_PATH;

public class VideoEditUploadActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {

    private Intent intent;
    private static final String TAG = "VideoEditUploadActivity";
    VideoView videoView;
    ImageButton imageButtonClose;
    Button buttonPost;
    EditText editTextDescription;
    String videoPath, audioPath, outputFilePath = "";
    CircularProgressView circularProgressView;
    TextView textViewUploadPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit_upload);

        circularProgressView = findViewById(R.id.circularProgressView);
        circularProgressView.setVisibility(View.GONE);

        videoView = findViewById(R.id.videoView);
        imageButtonClose = findViewById(R.id.imageButtonClose);
        textViewUploadPercentage = findViewById(R.id.textViewUploadPercentage);
        textViewUploadPercentage.setVisibility(View.INVISIBLE);
        buttonPost = findViewById(R.id.buttonPost);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageButtonClose.setOnClickListener(this);
        buttonPost.setOnClickListener(this);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        if (getIntent() != null) {
            audioPath = getIntent().getStringExtra(AUDIO_PATH);
            videoPath = getIntent().getStringExtra(VIDEO_PATH);
        }

        // Marge audio and video
        if (!audioPath.equals("")) {
            outputFilePath = mixVA(videoPath, audioPath);
        } else {
            videoView.setVideoPath(videoPath);
            videoView.start();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonClose:
                onBackPressed();
                break;

            case R.id.buttonPost:
                if (MyApplication.getInstanceOfUserModel().getFblogin() == 0) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                hideKeypad();
                if (!outputFilePath.equals("")) {
                    videoPath = outputFilePath;
                }
                uploadFile(videoPath, editTextDescription.getText().toString(), String.valueOf(MyApplication.getInstanceOfUserModel().getID()) + "");
                break;
        }
    }

    private void hideKeypad() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        videoView.suspend();
        videoView = null;
        super.onDestroy();
    }

    //    public void pickAThumbnail() {
//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, 100);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
//            //the image URI
//            Uri selectedImage = data.getData();
//
//        }
//    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Video.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void uploadFile(String filePath, String desc, String userId) {

        // Creating a file
        File file = new File(filePath);

        // Creating request body for file
//        RequestBody requestVideoFile = RequestBody.create(MediaType.parse("video/mp4"), file);

        ProgressRequestBody requestVideoFile = new ProgressRequestBody(file, this);

        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody uId = RequestBody.create(MediaType.parse("text/plain"), userId);

        // The GSON builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        // Creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyConstraints.API_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Creating our api
        ApiInterface api = retrofit.create(ApiInterface.class);

        // Creating a call and calling the upload image method
        Call<MyUploadPostResponseModel> call = api.uploadVideoPost(requestVideoFile, descBody, uId);

        // Finally performing the call
        call.enqueue(new Callback<MyUploadPostResponseModel>() {
            @Override
            public void onResponse(Call<MyUploadPostResponseModel> call, Response<MyUploadPostResponseModel> response) {
                try {
                    if (response.body().getResult().equals("Success")) {
                        circularProgressView.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Video Uploaded Successfully...", Toast.LENGTH_LONG).show();

                        intent = new Intent("upload.success");
                        sendBroadcast(intent);

                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again...", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                Log.d("ttt", "onResponse: " + response.body().getResult());
            }

            @Override
            public void onFailure(Call<MyUploadPostResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onProgressUpdate(int percentage) {
        Log.d(TAG, "onProgressUpdate: " + percentage);
        circularProgressView.setVisibility(View.VISIBLE);
        circularProgressView.setProgress(percentage);

        textViewUploadPercentage.setVisibility(View.VISIBLE);
        textViewUploadPercentage.setText(percentage + " %");
    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Upload failed, Try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFinish() {
        Log.d(TAG, "onFinish: ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getApplicationContext(), "Upload Done", Toast.LENGTH_SHORT).show();
//                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }


    /*
   * @param videoFile path to video file
   * @param audioFile path to audiofile
   */
    String filePath = "";

    public String mixVA(final String videoFile, final String audioFile) {

        Movie video = null;
        try {
            video = new MovieCreator().build(videoFile);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Movie audio = null;
        try { // audioFile
            audio = new MovieCreator().build(audioFile); //  filePath = MyConstraints.FILE_PATH + "La Isla Bonita By Alezee.mp4";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {

            e.printStackTrace();
            return null;
        }
        int size = audio.getTracks().size();
        Track audioTrack = audio.getTracks().get((size - 1));
        video.addTrack(audioTrack);

        Container out = new DefaultMp4Builder().build(video);

        File myDirectory = new File(MyConstraints.FILE_PATH + "/VIDEO/"); // Environment.getExternalStorageDirectory(), "/VideoMate"
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        filePath = myDirectory + "" + System.currentTimeMillis() + ".mp4";

        try {
            RandomAccessFile ram = new RandomAccessFile(String.format(filePath), "rw");
            FileChannel fc = ram.getChannel();
            out.writeContainer(fc);
            ram.close();

            // Play video after merging
            videoView.setVideoPath(filePath);
            videoView.start();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (filePath == null) {
            return "";
        }
        return filePath;
//        return "";
    }

}
