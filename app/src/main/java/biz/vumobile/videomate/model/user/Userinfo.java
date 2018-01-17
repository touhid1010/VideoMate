
package biz.vumobile.videomate.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * UserSingleton.class will return Userinfo singleton
 */

public class Userinfo {

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
    private String password;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Fblogin")
    @Expose
    private Integer fblogin;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Aboutme")
    @Expose
    private String aboutme;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getFblogin() {
        return fblogin;
    }

    public void setFblogin(Integer fblogin) {
        this.fblogin = fblogin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
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
