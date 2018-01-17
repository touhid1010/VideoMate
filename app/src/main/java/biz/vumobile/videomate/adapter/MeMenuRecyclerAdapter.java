package biz.vumobile.videomate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.model.user.MeMenuModel;

/**
 * Created by IT-10 on 1/16/2018.
 */

public class MeMenuRecyclerAdapter extends RecyclerView.Adapter<MeMenuRecyclerAdapter.MyMenuViewHolder> {

    private Context mContext;
    private List<MeMenuModel> meMenuModels = new ArrayList<>();

    private ItemClickListener itemClickListener;

    public MeMenuRecyclerAdapter(Context context, List<MeMenuModel> meMenuModels) {
        this.mContext = context;
        this.meMenuModels = meMenuModels;
    }

    @Override
    public MyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_me_menu_recyclerview, parent, false);
        return new MyMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyMenuViewHolder holder, int position) {
        holder.textViewMenuName.setText(meMenuModels.get(position).getMenuName());
        holder.imageViewMenuIcon.setImageDrawable(mContext.getResources().getDrawable(meMenuModels.get(position).getImageResource()));
    }

    @Override
    public int getItemCount() {
        return meMenuModels.size();
    }


    public class MyMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewMenuIcon;
        TextView textViewMenuName;

        MyMenuViewHolder(View itemView) {
            super(itemView);

            imageViewMenuIcon = itemView.findViewById(R.id.imageViewMenuIcon);
            textViewMenuName = itemView.findViewById(R.id.textViewMenuName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View v, int position);
    }


}










