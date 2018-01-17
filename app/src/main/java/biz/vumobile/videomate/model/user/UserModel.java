
package biz.vumobile.videomate.model.user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("Userinfo")
    @Expose
    private List<Userinfo> userinfo = null;

    public List<Userinfo> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(List<Userinfo> userinfo) {
        this.userinfo = userinfo;
    }

}
