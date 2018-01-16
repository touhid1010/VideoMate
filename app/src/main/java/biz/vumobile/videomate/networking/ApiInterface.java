package biz.vumobile.videomate.networking;


import biz.vumobile.videomate.model.receivedata.GetAllPostsClass;
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

    @GET(MyConstraints.GET_ALL_POSTS)
    Call<GetAllPostsClass> getPosts();

    @Multipart
    @POST(MyConstraints.API_POST_DATA)
    Call<MyUploadPostResponseModel> uploadVideoPost(@Part(("videos\"; filename=\"a.mp4\" ")) RequestBody videoFile,
                                                    @Part("description") RequestBody description,
                                                    @Part("userid") RequestBody userid);

}