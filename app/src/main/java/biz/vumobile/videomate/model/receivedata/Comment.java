package biz.vumobile.videomate.model.receivedata;

/**
 * Created by toukirul on 18/1/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("tbl_Video")
    @Expose
    private Object tblVideo;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("VideoID")
    @Expose
    private String videoID;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("TimeStamp")
    @Expose
    private String timeStamp;

    public Object getTblVideo() {
        return tblVideo;
    }

    public void setTblVideo(Object tblVideo) {
        this.tblVideo = tblVideo;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}