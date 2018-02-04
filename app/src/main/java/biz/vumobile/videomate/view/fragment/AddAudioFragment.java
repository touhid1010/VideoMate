package biz.vumobile.videomate.view.fragment;

import android.content.res.ColorStateList;
import android.graphics.Camera;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.AddAudioAdapter;
import biz.vumobile.videomate.login.MyLoginOperation;
import biz.vumobile.videomate.model.receivedata.GetAllPostsClass;
import biz.vumobile.videomate.model.receivedata.audio.AudioModel;
import biz.vumobile.videomate.model.receivedata.audio.Song;
import biz.vumobile.videomate.model.receivedata.audiocat.AudioCatModel;
import biz.vumobile.videomate.model.receivedata.audiocat.Category;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.MyConstraints;
import biz.vumobile.videomate.view.activity.CameraActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IT-10 on 1/23/2018.
 */

public class AddAudioFragment extends Fragment implements View.OnClickListener, AddAudioAdapter.ClickCallback {

    LinearLayout linearLayoutCategoryButton;
    ImageView imageButtonClose;
    RecyclerView recyclerViewAudioList;
    AddAudioAdapter addAudioAdapter;
    List<Song> audioModels = new ArrayList<>();

    ProgressBar progressBar;

    private ApiInterface apiInterface;
    private Call<AudioCatModel> audioCatModelCall;
    private Call<AudioModel> audioModelCall;

    MediaPlayer mp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_audio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Try again", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        linearLayoutCategoryButton = view.findViewById(R.id.linearLayoutCategoryButton);
        imageButtonClose = view.findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(this);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerViewAudioList = view.findViewById(R.id.recyclerViewAudioList);
        addAudioAdapter = new AddAudioAdapter(getActivity(), audioModels);
        recyclerViewAudioList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAudioList.setAdapter(addAudioAdapter);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        addAudioAdapter.setClickCallback(this);

        loadAudioCatData();
        loadDataFromCategory(1); // Default load bangle song
    }

    @Override
    public void onPlayClick(View v, int position) {
        String mUrl = audioModels.get(position).getSongPath().trim().replaceAll(" ", "%20");

        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        if (mp != null) {
            try {
                mp.reset();
                mp.setDataSource(mUrl);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAddClick(View v, int position) {
        CameraActivity cameraActivity = new CameraActivity();
        //  new DownloadAudioFile().execute(audioModels.get(position).getSongPath(), audioModels.get(position).getSongName());
        cameraActivity.fireFromAddAudioFragment(audioModels.get(position).getSongPath(), audioModels.get(position).getSongName());
        // Hide fragment
        getActivity().onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonClose:
                getActivity().onBackPressed();
                break;
        }
    }

    public void loadAudioCatData() {
        audioCatModelCall = apiInterface.getAudioCatList();
        audioCatModelCall.enqueue(new Callback<AudioCatModel>() {
            @Override
            public void onResponse(Call<AudioCatModel> call, Response<AudioCatModel> response) {
                generateDynamicButton(response.body().getCategory());
            }

            @Override
            public void onFailure(Call<AudioCatModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void generateDynamicButton(List<Category> categories) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout linearLayout2 = new LinearLayout(getActivity());
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < categories.size(); i++) {
            Button button = new Button(getActivity());
            button.setText(categories.get(i).getName());
            button.setId(categories.get(i).getID());
            button.setBackground(getResources().getDrawable(R.drawable.button_gradient));
            button.setTextColor(getResources().getColor(R.color.background_color_white));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDataFromCategory(v.getId());
                }
            });

            // If more than 3 data found make it double line
            if (i <= 2) {
                linearLayout.addView(button);
            } else {
                linearLayout2.addView(button);
            }

        }
        linearLayoutCategoryButton.addView(linearLayout);
        linearLayoutCategoryButton.addView(linearLayout2);


    }

    public void loadDataFromCategory(int vId) {
        audioModels.clear();
        audioModelCall = apiInterface.getAudioList(String.valueOf(vId));
        audioModelCall.enqueue(new Callback<AudioModel>() {
            @Override
            public void onResponse(Call<AudioModel> call, Response<AudioModel> response) {
                audioModels.addAll(response.body().getSongs());
                Log.d("t", "onResponse: " + response.body());
                addAudioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AudioModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            mp.release();
            mp.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

















