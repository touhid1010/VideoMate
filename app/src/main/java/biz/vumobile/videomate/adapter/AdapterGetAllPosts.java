package biz.vumobile.videomate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.model.receivedata.Video;

/**
 * Created by toukirul on 15/1/2018.
 */

public class AdapterGetAllPosts extends RecyclerView.Adapter<AdapterGetAllPosts.MyViewHolder> {

    private Context mContext;
    private List<Video> resultList;
    private Random mRandom = new Random();

    public AdapterGetAllPosts(Context context, List<Video> list) {
        this.mContext = context;
        this.resultList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_popular, parent, false);

        return new MyViewHolder(view);
    }

    Random r = new Random();
    int start = 600;
    int end = 470;

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Video result = resultList.get(position);
        Log.d("Response", "lol " + result.getDescription());

        holder.imgItem.getLayoutParams().height = r.nextInt(start - end) + end; // getRandomIntInRange(330, 329);

        holder.imgItem.setBackgroundColor(getRandomHSVColor());
        holder.txtTitle.setText(result.getDescription());
        holder.txtViewsCount.setText(String.valueOf(result.getView()));
        Glide.with(mContext).load(result.getThumbnail()).into(holder.imgItem);
        Glide.with(mContext).load(result.getUser().getImageUrl()).placeholder(R.drawable.user).into(holder.imgUser);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItem, imgUser;
        private TextView txtTitle, txtViewsCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtViewsCount = itemView.findViewById(R.id.txtViewsCount);
            imgItem = itemView.findViewById(R.id.imgItem);
            imgUser = itemView.findViewById(R.id.imgUser);
            txtTitle = itemView.findViewById(R.id.txtContentTitle);
        }
    }

    protected int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt((max - min) + min) + min;
    }

    protected int getRandomHSVColor() {
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);
        // We make the color depth full
        float saturation = 0.3f;
        // We make a full bright color
        float value = 1.0f;
        // We avoid color transparency
        int alpha = 255;
        // Finally, generate the color
        int color = Color.HSVToColor(alpha, new float[]{hue, saturation, value});
        // Return the color
        return color;
    }
}
