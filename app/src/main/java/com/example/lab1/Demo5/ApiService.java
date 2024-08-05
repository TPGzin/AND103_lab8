package com.example.lab1.Demo5;
// ApiService.java
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/sach")
    Call<List<Sach>> getAllSach();

    @GET("/sach/{id}")
    Call<Sach> getSachById(@Path("id") String id);

    @POST("/sach")
    Call<Sach> createSach(@Body Sach sach);

    @PUT("/sach/{id}")
    Call<Sach> updateSach(@Path("id") String id, @Body Sach sach);

    @DELETE("/sach/{id}")
    Call<Void> deleteSach(@Path("id") String id);

    @GET("/sach/search")
    Call<List<Sach>> searchSach(@Query("q") String query);
}
