package com.example.moviecatalogue.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.moviecatalogue.models.MovieTvResponse;
import com.example.moviecatalogue.repositories.TvRepository;





public class TvViewModel extends ViewModel {
    private MutableLiveData<MovieTvResponse> mTv;
    private TvRepository tvRepo;
    private MutableLiveData<MovieTvResponse> tvShow;

    public void init(){
        if(mTv != null){
            return;
        }
        tvRepo = TvRepository.getInstance();
        mTv = tvRepo.getTvShow();
    }

    public void getSearch(String query){
        tvRepo = TvRepository.getInstance();
        tvShow = tvRepo.getSearchTvShow(query);
    }


    public MutableLiveData<MovieTvResponse> getSearchTv(){return tvShow;}
    public MutableLiveData<MovieTvResponse> getTvShow(){
        return mTv;
    }
}
