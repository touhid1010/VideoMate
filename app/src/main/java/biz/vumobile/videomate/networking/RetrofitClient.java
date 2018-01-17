package biz.vumobile.videomate.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by toukirul on 9/1/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String basUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(basUrl)
                    .build();
        }
        return retrofit;
    }
}