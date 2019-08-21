package com.example.moviecatalogue.api;

import com.example.moviecatalogue.models.MovieTvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("3/discover/movie?")
    Call<MovieTvResponse> getMovie(@Query("api_key") String apiKey);

    @GET("3/discover/tv?")
    Call<MovieTvResponse> getTvShow(@Query("api_key") String apiKey);

    @GET("3/search/movie")
    Call<MovieTvResponse> getSearchMovie(@Query("api_key") String apiKey,@Query("query") String query);

    @GET("3/search/tv")
    Call<MovieTvResponse> getSearchTv(@Query("api_key") String apiKey,@Query("query") String query);

    @GET("3/discover/movie")
    Call<MovieTvResponse> getNowMovie(@Query("api_key") String apiKey,@Query("primary_release_date.gte") String DateGte,@Query("primary_release_date.lte") String DateLte);

}
