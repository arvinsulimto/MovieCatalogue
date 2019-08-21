package com.example.moviecatalogue.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.moviecatalogue.models.MovieTvResponse;
import com.example.moviecatalogue.repositories.MovieRepository;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieTvResponse> mMovies;
    private MovieRepository MovieRepo;
    private MutableLiveData<MovieTvResponse> movieNow;
    private MutableLiveData<MovieTvResponse> movieSearch;

    public MovieViewModel(){

    }

    public void init(){
        if(mMovies != null){
            return;
        }
        MovieRepo = MovieRepository.getInstance();
        mMovies = MovieRepo.getMovie();
    }

    public void getSearch(String query){
        MovieRepo = MovieRepository.getInstance();
        movieSearch = MovieRepo.getSearchMovie(query);
    }

    public void getNowMovie(String dateGte,String dateLte){
        MovieRepo = MovieRepository.getInstance();
        movieNow = MovieRepo.getNowMovie(dateGte,dateLte);
    }

    public LiveData<MovieTvResponse> getNowMovieByDate(){ return movieNow;}

    public LiveData<MovieTvResponse> getSearchMovie(){return movieSearch;}

    public LiveData<MovieTvResponse> getMovie(){
        return mMovies;
    }

}
