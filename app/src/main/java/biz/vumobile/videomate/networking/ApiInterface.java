package biz.vumobile.videomate.networking;


import java.util.Map;

import biz.vumobile.videomate.model.user.NewUserRegisterResult;
import biz.vumobile.videomate.model.receivedata.GetAllPostsClass;
import biz.vumobile.videomate.model.senddata.MyUploadPostResponseModel;
import biz.vumobile.videomate.model.user.UserModel;
import biz.vumobile.videomate.model.user.Userinfo;
import biz.vumobile.videomate.utils.MyConstraints;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by toukirul on 9/1/2018.
 */

public interface ApiInterface {

    @POST(MyConstraints.API_REGISTER_USER)
    Call<NewUserRegisterResult> registerUser(@Body Userinfo userinfo);

    @GET(MyConstraints.API_GET_USER)
    Call<UserModel> getUserById(@Query("id") String uId);

    @GET(MyConstraints.GET_ALL_POSTS)
    Call<GetAllPostsClass> getPosts();

    @Multipart
    @POST(MyConstraints.API_POST_DATA)
    Call<MyUploadPostResponseModel> uploadVideoPost(@Part(("videos\"; filename=\"a.mp4\" ")) RequestBody videoFile,
                                                    @Part("description") RequestBody description,
                                                    @Part("userid") RequestBody userid);

}
