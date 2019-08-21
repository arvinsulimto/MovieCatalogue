package com.example.moviecatalogue.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MoviesTvAdapter;
import com.example.moviecatalogue.models.MoviesTv;
import com.example.moviecatalogue.viewmodels.FavouriteViewModel;


import java.util.ArrayList;

public class FavouriteMovieFragment extends Fragment {


    private MoviesTvAdapter adapter;

    public FavouriteMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FavouriteViewModel fViewModel = new FavouriteViewModel(getContext());
        View view =  inflater.inflate(R.layout.fragment_favourite_movie, container, false);

        ArrayList<MoviesTv> data = fViewModel.getFavouriteMovie();


        RecyclerView rvMovies = view.findViewById(R.id.rv_fav_movie);
        rvMovies.setHasFixedSize(true);
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        adapter = new MoviesTvAdapter(data, 1);
        rvMovies.setAdapter(adapter);


        return view;
    }


}
