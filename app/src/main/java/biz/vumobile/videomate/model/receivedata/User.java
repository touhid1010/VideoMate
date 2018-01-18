
package biz.vumobile.videomate.model.receivedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("ImageUrl")
    @Expose
    private Object imageUrl;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

}
