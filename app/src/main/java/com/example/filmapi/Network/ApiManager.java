package com.example.filmapi.Network;

import com.example.filmapi.Response.BaseResponseDto;
import com.example.filmapi.Response.HomeContentDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {

    String SERVER = "http://10.0.2.2:8081" ;
    @GET("/api/home/getAll")
    Call<BaseResponseDto<HomeContentDto>> apiHome();
}
