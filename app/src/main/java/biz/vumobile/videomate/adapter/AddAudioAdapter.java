package biz.vumobile.videomate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.model.receivedata.audio.Song;


public class AddAudioAdapter extends RecyclerView.Adapter<AddAudioAdapter.MyViewHolder> {

    Context mContext;
    List<Song> mSongs;
    ClickCallback clickCallback;

    public AddAudioAdapter(Context context, List<Song> songs) {
        mContext = context;
        mSongs = songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_audio_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(mContext).load(mSongs.get(position).getThumbnail() + "").placeholder(R.drawable.play_audio).into(holder.imageViewThumbnail);
        holder.textViewSongName.setText(mSongs.get(position).getSongName());
        holder.textViewSongTime.setText(mSongs.get(position).getSongTime());
        holder.textViewArtist.setText(mSongs.get(position).getArtist());

    }


    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewThumbnail;
        TextView textViewSongName, textViewSongTime, textViewArtist;
        ImageButton imageButtonAddAudio;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
            textViewSongTime = itemView.findViewById(R.id.textViewSongTime);
            textViewArtist = itemView.findViewById(R.id.textViewArtist);
            imageButtonAddAudio = itemView.findViewById(R.id.imageButtonAddAudio);

            imageViewThumbnail.setOnClickListener(this);
            imageButtonAddAudio.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickCallback == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.imageViewThumbnail:
                    clickCallback.onPlayClick(v, getAdapterPosition());
                    break;

                case R.id.imageButtonAddAudio:
                    clickCallback.onAddClick(v, getAdapterPosition());
                    break;
            }
        }
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public interface ClickCallback {
        void onPlayClick(View v, int position);

        void onAddClick(View v, int position);
    }

}
