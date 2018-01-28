
package biz.vumobile.videomate.model.receivedata.audio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("SongName")
    @Expose
    private String songName;
    @SerializedName("SongTime")
    @Expose
    private String songTime;
    @SerializedName("Artist")
    @Expose
    private String artist;
    @SerializedName("SongPath")
    @Expose
    private String songPath;
    @SerializedName("Thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongTime() {
        return songTime;
    }

    public void setSongTime(String songTime) {
        this.songTime = songTime;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
