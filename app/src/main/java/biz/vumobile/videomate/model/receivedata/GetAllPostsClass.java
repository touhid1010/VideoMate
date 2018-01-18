
package biz.vumobile.videomate.model.receivedata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPostsClass {

    @SerializedName("Videos")
    @Expose
    private List<Video> videos = null;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

}
