
package biz.vumobile.videomate.model.receivedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("VideoId")
    @Expose
    private Integer videoId;
    @SerializedName("Thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("VideoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("Like")
    @Expose
    private Integer like;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Comment")
    @Expose
    private Integer comment;
    @SerializedName("View")
    @Expose
    private Integer view;
    @SerializedName("Follow")
    @Expose
    private Integer Follow;

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getFollow() {
        return Follow;
    }

    public void setFollow(Integer follow) {
        Follow = follow;
    }
}
