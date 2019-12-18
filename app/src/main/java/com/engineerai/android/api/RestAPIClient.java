package com.engineerai.android.api;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIClient {

    private static Retrofit retrofit = null;

    public static RestApiMethod getRestApiMethods() {
        return createRetrofit().create(RestApiMethod.class);
    }

    private static Retrofit createRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = getBuilder();
            httpClient.protocols(Arrays.asList(Protocol.HTTP_1_1));
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://sd2-hiring.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    @NonNull
    private static OkHttpClient.Builder getBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
       /// if (BuildConfig.IS_DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout((long)60 * 3, TimeUnit.SECONDS)
                .readTimeout((long)60 * 3, TimeUnit.SECONDS)
                .writeTimeout((long)60 * 3, TimeUnit.SECONDS);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient;
    }
}
