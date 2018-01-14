//package biz.vumobile.videomate.view.fragment;
//
//import android.content.Intent;
//import android.graphics.PointF;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.otaliastudios.cameraview.CameraException;
//import com.otaliastudios.cameraview.CameraListener;
//import com.otaliastudios.cameraview.CameraOptions;
//import com.otaliastudios.cameraview.CameraView;
//import com.otaliastudios.cameraview.Facing;
//import com.otaliastudios.cameraview.SessionType;
//import com.otaliastudios.cameraview.WhiteBalance;
//
//import java.io.File;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import biz.vumobile.videomate.R;
//import biz.vumobile.videomate.view.activity.VideoEditUploadActivity;
//import biz.vumobile.videomate.utils.MyConstraints;
//
//import static biz.vumobile.videomate.utils.MyConstraints.VIDEO_RECORD_TIME;
//
///**
// * Created by IT-10 on 1/9/2018.
// */
//
//public class CameraFragment extends Fragment implements View.OnClickListener {
//
//    CameraView cameraView;
//    ImageButton imageButtonRecord, imageButtonSwitchCamera;
//
//    ImageView imageViewRecAnim;
//    TextView textViewRecordDuration;
//
//    long recTimeToShow = 0;
//    boolean runLoop = false;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_video, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        cameraView = view.findViewById(R.id.cameraView);
//        cameraView.setSessionType(SessionType.VIDEO);
//
////        imageViewRecAnim = view.findViewById(R.id.imageViewRecAnim);
//        textViewRecordDuration = view.findViewById(R.id.textViewRecordDuration);
//        imageButtonRecord = view.findViewById(R.id.imageButtonRecord);
//        imageButtonSwitchCamera = view.findViewById(R.id.imageButtonSwitchCamera);
//        imageButtonRecord.setOnClickListener(this);
//        imageButtonSwitchCamera.setOnClickListener(this);
//
//        cameraView.setWhiteBalance(WhiteBalance.AUTO);
//
//        cameraView.addCameraListener(new CameraListener() {
//            @Override
//            public void onCameraOpened(CameraOptions options) {
//                super.onCameraOpened(options);
//                // Toast.makeText(getActivity(), "Opened", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCameraClosed() {
//                super.onCameraClosed();
//            }
//
//            @Override
//            public void onCameraError(@NonNull CameraException exception) {
//                super.onCameraError(exception);
//                imageButtonRecord.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            public void onVideoTaken(File video) {
//                super.onVideoTaken(video);
//
//                hideVideoDuration();
//                goToEditVideo(video.getPath());
//            }
//
//            @Override
//            public void onFocusStart(PointF point) {
//                super.onFocusStart(point);
//                Toast.makeText(getActivity(), "Focus start", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFocusEnd(boolean successful, PointF point) {
//                super.onFocusEnd(successful, point);
//                Toast.makeText(getActivity(), "Focus end", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // To show record time
//        showVideoDuration();
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.imageButtonRecord:
//                String timeName = String.valueOf(System.currentTimeMillis()); // getStorageDir(getActivity(), timeName+".mp4")
//                File file = new File("/storage/emulated/0/Touhid/");
//                file.mkdir();
//
//                cameraView.startCapturingVideo(new File(file, timeName + ".mp4"), VIDEO_RECORD_TIME);
//                imageButtonRecord.setVisibility(View.INVISIBLE);
//
//                runLoop = true;
//
//                break;
//
//            case R.id.imageButtonSwitchCamera:
//                if (cameraView.getFacing() == Facing.FRONT) {
//                    cameraView.setFacing(Facing.BACK);
//                    imageButtonSwitchCamera.setImageResource(R.drawable.ic_action_camera_front);
//                } else {
//                    cameraView.setFacing(Facing.FRONT);
//                    imageButtonSwitchCamera.setImageResource(R.drawable.ic_action_camera_rear);
//                }
//                break;
//
//
//        }
//
//    }
//
//    public void goToEditVideo(String videoPath) {
//        imageButtonRecord.setVisibility(View.VISIBLE);
//
//        Intent intent = new Intent(getActivity(), VideoEditUploadActivity.class);
//        intent.putExtra(MyConstraints.VIDEO_PATH, videoPath);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        cameraView.start();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        hideVideoDuration();
//        cameraView.stop();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        cameraView.destroy();
//    }
//
//    public void hideVideoDuration() {
//        runLoop = false;
//    }
//
//    public void showVideoDuration() {
//        // Make runLoop true of false to show record duration
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (!runLoop) {
//                    recTimeToShow = 0;
//                    if (getActivity() != null)
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (textViewRecordDuration != null) {
//                                    textViewRecordDuration.setText("");
//                                }
//
//                            }
//                        });
//                    return;
//                }
//
//                if (getActivity() != null)
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (MyConstraints.VIDEO_RECORD_TIME / 1000 < recTimeToShow) {
//                                return;
//                            }
//                            if (textViewRecordDuration != null) {
//                                textViewRecordDuration.setText(String.valueOf(recTimeToShow) + " sec");
//                            }
//                        }
//                    });
//
//                recTimeToShow++;
//            }
//        }, 0, 1000);
//    }
//
//
//}
