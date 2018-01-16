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
import biz.vumobile.videomate.model.receivedata.Result;

/**
 * Created by toukirul on 15/1/2018.
 */

public class AdapterGetAllPosts extends RecyclerView.Adapter<AdapterGetAllPosts.MyViewHolder> {

    private Context mContext;
    private List<Result> resultList;
    private Random mRandom = new Random();

    public AdapterGetAllPosts(Context context, List<Result> list){
        this.mContext = context;
        this.resultList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_popular, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Result result = resultList.get(position);
        Log.d("Response","lol "+result.getDescription());
        holder.imgItem.getLayoutParams().height = getRandomIntInRange(300, 250);
        holder.imgItem.setBackgroundColor(getRandomHSVColor());
        holder.txtTitle.setText(result.getDescription());
        Glide.with(mContext).load(result.getThumbnailUrl()).into(holder.imgItem);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgItem;
        private TextView txtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtTitle = itemView.findViewById(R.id.txtContentTitle);
        }
    }

    protected int getRandomIntInRange(int max, int min){
        return mRandom.nextInt((max-min)+min)+min;
    }

    protected int getRandomHSVColor(){
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
