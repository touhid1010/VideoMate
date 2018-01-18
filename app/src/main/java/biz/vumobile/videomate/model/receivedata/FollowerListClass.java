package biz.vumobile.videomate.model.receivedata;

/**
 * Created by toukirul on 18/1/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowerListClass {

    @SerializedName("tbl_Video")
    @Expose
    private List<Object> tblVideo = null;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private Object password;
    @SerializedName("ImageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("Fblogin")
    @Expose
    private Integer fblogin;
    @SerializedName("Gender")
    @Expose
    private Object gender;
    @SerializedName("Aboutme")
    @Expose
    private Object aboutme;
    @SerializedName("TotalUploadedVideo")
    @Expose
    private Integer totalUploadedVideo;
    @SerializedName("Following")
    @Expose
    private Integer following;
    @SerializedName("Followers")
    @Expose
    private Integer followers;
    @SerializedName("TimeStamp")
    @Expose
    private String timeStamp;

    public List<Object> getTblVideo() {
        return tblVideo;
    }

    public void setTblVideo(List<Object> tblVideo) {
        this.tblVideo = tblVideo;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getFblogin() {
        return fblogin;
    }

    public void setFblogin(Integer fblogin) {
        this.fblogin = fblogin;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getAboutme() {
        return aboutme;
    }

    public void setAboutme(Object aboutme) {
        this.aboutme = aboutme;
    }

    public Integer getTotalUploadedVideo() {
        return totalUploadedVideo;
    }

    public void setTotalUploadedVideo(Integer totalUploadedVideo) {
        this.totalUploadedVideo = totalUploadedVideo;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}