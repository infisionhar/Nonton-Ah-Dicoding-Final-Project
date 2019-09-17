package com.hariz.noah.Network;

import com.hariz.noah.Model.Response.MovieResponse;
import com.hariz.noah.Model.Response.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers("Accept: application/json")
    @GET("3/movie/popular")
    Call<MovieResponse> getMovie(@Query("api_key") String apiKey);

    @Headers("Accept: application/json")
    @GET("3/tv/popular")
    Call<TvResponse> getTvMovie(@Query("api_key") String apiKey);

}
