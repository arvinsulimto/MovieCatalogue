package com.example.moviecatalogue.repositories;

import android.arch.lifecycle.MutableLiveData;
import com.example.moviecatalogue.api.APIClient;
import com.example.moviecatalogue.api.APIInterface;
import com.example.moviecatalogue.models.MovieTvResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    private static MovieRepository instance;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    public MutableLiveData<MovieTvResponse> getMovie(){
        final MutableLiveData<MovieTvResponse> data = new MutableLiveData<>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final Call<MovieTvResponse> call = apiInterface.getMovie("6b34c310bf6817eca80cff209c0e1f33");
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

    public MutableLiveData<MovieTvResponse> getSearchMovie(String query){
        final MutableLiveData<MovieTvResponse> data = new MutableLiveData<>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final Call<MovieTvResponse> call = apiInterface.getSearchMovie("6b34c310bf6817eca80cff209c0e1f33",query);
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

    public MutableLiveData<MovieTvResponse> getNowMovie(String dateGte,String dateLte){
        final MutableLiveData<MovieTvResponse> data = new MutableLiveData<>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final Call<MovieTvResponse> call = apiInterface.getNowMovie("6b34c310bf6817eca80cff209c0e1f33",dateGte,dateLte);
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
