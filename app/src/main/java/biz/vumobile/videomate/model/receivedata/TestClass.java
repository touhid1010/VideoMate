package biz.vumobile.videomate.model.receivedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestClass {

    @SerializedName("contentcode")
    @Expose
    private String contentcode;
    @SerializedName("catgorycode")
    @Expose
    private String catgorycode;
    @SerializedName("ContentTile")
    @Expose
    private String contentTile;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("physicalfilename")
    @Expose
    private String physicalfilename;
    @SerializedName("zid")
    @Expose
    private String zid;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("totalLike")
    @Expose
    private Integer totalLike;
    @SerializedName("totalView")
    @Expose
    private Integer totalView;
    @SerializedName("TimeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("LiveDate")
    @Expose
    private String liveDate;
    @SerializedName("ExpireDate")
    @Expose
    private String expireDate;

    public String getContentcode() {
        return contentcode;
    }

    public void setContentcode(String contentcode) {
        this.contentcode = contentcode;
    }

    public String getCatgorycode() {
        return catgorycode;
    }

    public void setCatgorycode(String catgorycode) {
        this.catgorycode = catgorycode;
    }

    public String getContentTile() {
        return contentTile;
    }

    public void setContentTile(String contentTile) {
        this.contentTile = contentTile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhysicalfilename() {
        return physicalfilename;
    }

    public void setPhysicalfilename(String physicalfilename) {
        this.physicalfilename = physicalfilename;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalView() {
        return totalView;
    }

    public void setTotalView(Integer totalView) {
        this.totalView = totalView;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}