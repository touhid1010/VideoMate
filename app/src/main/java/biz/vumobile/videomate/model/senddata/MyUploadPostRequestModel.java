package biz.vumobile.videomate.model.senddata;

/**
 * Created by IT-10 on 1/11/2018.
 */

public class MyUploadPostRequestModel {
    // {"description":"dfwsf", "userid":""}

    private String result = "";

    public MyUploadPostRequestModel(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
