package biz.vumobile.videomate.model.receivedata;

/**
 * Created by toukirul on 11/1/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentsClass {

    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}