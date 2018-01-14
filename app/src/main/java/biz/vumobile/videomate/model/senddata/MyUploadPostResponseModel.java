package biz.vumobile.videomate.model.senddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IT-10 on 1/11/2018.
 */

public class MyUploadPostResponseModel {
    // {"result": "Success"}

    @SerializedName("result")
    @Expose
    private String result="";

    public MyUploadPostResponseModel(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
