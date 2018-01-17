package biz.vumobile.videomate.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IT-10 on 1/17/2018.
 */

public class NewUserRegisterResult {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("Id")
    @Expose
    private Integer id;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
