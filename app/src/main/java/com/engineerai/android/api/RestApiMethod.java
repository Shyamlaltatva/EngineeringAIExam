package com.engineerai.android.api;



import com.engineerai.android.model.responsemodel.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiMethod {
    @GET("users")
    Call<APIResponse> getList(@Query("offset") int pageNo, @Query("limit") int limit);
}
