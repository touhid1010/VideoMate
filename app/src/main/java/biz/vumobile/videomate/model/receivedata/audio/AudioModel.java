
package biz.vumobile.videomate.model.receivedata.audio;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AudioModel {

    @SerializedName("songs")
    @Expose
    private List<Song> songs = null;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}
