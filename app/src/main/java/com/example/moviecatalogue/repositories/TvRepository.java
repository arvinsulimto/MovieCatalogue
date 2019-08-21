package com.example.moviecatalogue.repositories;

import android.arch.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.moviecatalogue.api.APIClient;
import com.example.moviecatalogue.api.APIInterface;
import com.example.moviecatalogue.models.MovieTvResponse;

public class TvRepository {
    public static TvRepository instance;

    public static TvRepository getInstance(){
        if(instance == null){
         instance = new TvRepository();
        }
        return instance;
    }

    public MutableLiveData<MovieTvResponse> getTvShow(){
        final MutableLiveData<MovieTvResponse> data = new MutableLiveData<>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final Call<MovieTvResponse> call = apiInterface.getTvShow("6b34c310bf6817eca80cff209c0e1f33");

        call.enqueue(new Callback<MovieTvResponse>() {
            @Override
            public void onResponse(Call<MovieTvResponse> call, Response<MovieTvResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieTvResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }


    public MutableLiveData<MovieTvResponse> getSearchTvShow(String query){
        final MutableLiveData<MovieTvResponse> data = new MutableLiveData<>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final Call<MovieTvResponse> call = apiInterface.getSearchTv("6b34c310bf6817eca80cff209c0e1f33",query);

        call.enqueue(new Callback<MovieTvResponse>() {
            @Override
            public void onResponse(Call<MovieTvResponse> call, Response<MovieTvResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieTvResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
