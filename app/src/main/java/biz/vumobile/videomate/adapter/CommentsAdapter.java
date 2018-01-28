package biz.vumobile.videomate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.model.receivedata.Comment;

/**
 * Created by toukirul on 11/1/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyCommentViewHolder> {

    private Context mCOntext;
    private List<Comment> commentsClassList;

    public CommentsAdapter(Context context, List<Comment> list){
        this.mCOntext = context;
        this.commentsClassList = list;
    }

    @Override
    public MyCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCOntext).inflate(R.layout.row_comment, parent, false);

        return new MyCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCommentViewHolder holder, int position) {

        Comment commentsClass = commentsClassList.get(position);
        Log.d("Comments",commentsClass.getComment());
        holder.txtUserNameComment.setText(commentsClass.getID());
        holder.txtUserComments.setText(commentsClass.getComment());
        holder.txtDateTime.setText(commentsClass.getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return commentsClassList.size();
    }

    public class MyCommentViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUserNameComment, txtUserComments, txtDateTime;

        public MyCommentViewHolder(View itemView) {
            super(itemView);

            txtUserNameComment = itemView.findViewById(R.id.txtUserNameComment);
            txtUserComments = itemView.findViewById(R.id.txtUserComments);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
        }
    }
}
