package com.example.moviecatalogue.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTvResponse {

    @SerializedName("results")
    List<MoviesTv> listData;
    private static MovieTvResponse instance;
    public List<MoviesTv> getListData()
    {
        return listData;
    }

    public static MovieTvResponse getInstance(){
        if(instance == null){
            instance = new MovieTvResponse();
        }
        return instance;
    }

    public void setDataMovie(List<MoviesTv> movtv){
        listData = movtv;
    }



}
