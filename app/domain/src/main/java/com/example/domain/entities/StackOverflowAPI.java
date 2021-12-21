package com.example.domain.entities;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StackOverflowAPI {
//    @GET("/api/v1/login")
////    Call<StackOverflowQuestions> loadQuestion(@Query("tagged") String tags);
//    Call<StackOverflowQuestions> isValidUser(
//            @Query("ssid") String ssid,
//            @Query("status") String status,
//            @Query("err") String err);
    //login API
    @POST("/api/v1/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("user") String username,
                     @Field("password") String password);
    //load video API
    @POST("/api/v1/videoList")
    @FormUrlEncoded
    Call<Video> loadVideo(@Field("start_index") String stid,@Field("count") String cnt,@Field("access_token") String ssid);
}
