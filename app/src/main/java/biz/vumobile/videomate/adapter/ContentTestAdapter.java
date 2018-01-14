package biz.vumobile.videomate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.model.receivedata.TestClass;

/**
 * Created by toukirul on 9/1/2018.
 */

public class ContentTestAdapter extends RecyclerView.Adapter<ContentTestAdapter.MyViewHolder>{

    private Context mContext;
    private List<TestClass> newTestClass;

    public ContentTestAdapter(Context context, List<TestClass> list) {
        this.mContext = context;
        this.newTestClass = list;
    }

    @Override
    public ContentTestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_popular, parent, false);
        return new ContentTestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentTestAdapter.MyViewHolder holder, int position) {

        final TestClass newGame = newTestClass.get(position);

        Glide.with(mContext).load(newGame.getImageUrl()).into(holder.imgItem);
        holder.txtContentTitle.setText(newGame.getContentTile());
    }

    @Override
    public int getItemCount() {
        return newTestClass.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItem;
        private TextView txtContentTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtContentTitle = itemView.findViewById(R.id.txtContentTitle);
        }
    }
}