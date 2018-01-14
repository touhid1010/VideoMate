package biz.vumobile.videomate.networking;


import java.util.List;

import biz.vumobile.videomate.model.receivedata.TestClass;
import biz.vumobile.videomate.model.senddata.MyUploadPostResponseModel;
import biz.vumobile.videomate.utils.MyConstraints;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by toukirul on 9/1/2018.
 */

public interface ApiInterface {

    String BASE_URL = "http://wap.shabox.mobi/";

    @GET("GCMPanel/ClubzAPI.aspx?cat=Slider")
    Call<List<TestClass>> getContents();

    @Multipart
    @POST(MyConstraints.API_POST_DATA)
    Call<MyUploadPostResponseModel> uploadVideoPost(@Part(("videos\"; filename=\"a.mp4\" ")) RequestBody videoFile,
                                                    @Part("description") RequestBody description,
                                                    @Part("userid") RequestBody userid);

}