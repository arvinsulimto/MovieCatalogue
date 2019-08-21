package com.example.moviecatalogue.fragment;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.SettingActivity;
import com.example.moviecatalogue.adapter.MoviesTvAdapter;
import com.example.moviecatalogue.models.MovieTvResponse;
import com.example.moviecatalogue.models.MoviesTv;
import com.example.moviecatalogue.viewmodels.MovieViewModel;
import java.util.ArrayList;
import java.util.List;


public class MovieFragment extends Fragment{



    private MovieViewModel mMovieViewModel;
    private MoviesTvAdapter adapter;
    private List<MoviesTv> listData = new ArrayList<>();
    private ProgressBar progressBar;
    private Toolbar toolbar;

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

            mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
            mMovieViewModel.init();
            progressBar.setVisibility(View.VISIBLE);
            mMovieViewModel.getMovie().observe(this, new Observer<MovieTvResponse>() {
                @Override
                public void onChanged(@Nullable MovieTvResponse movieTvResponse) {
                    progressBar.setVisibility(View.GONE);
                    if(movieTvResponse == null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.errorFetch), Toast.LENGTH_LONG).show();
                    }
                    else{
                        listData.clear();
                        listData.addAll(movieTvResponse.getListData());
                        adapter.notifyDataSetChanged();

                    }

                }
            });


        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);

        rvMovies.setHasFixedSize(true);
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else {
            rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }


        adapter = new MoviesTvAdapter(listData, 1);
        rvMovies.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_nav_menu, menu);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);

        if(searchManager != null){
            SearchView searchView = (SearchView)(menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    mMovieViewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
                    mMovieViewModel.getSearch(s);
                    progressBar.setVisibility(View.VISIBLE);
                    mMovieViewModel.getSearchMovie().observe(getActivity(), new Observer<MovieTvResponse>() {
                        @Override
                        public void onChanged(@Nullable MovieTvResponse movieTvResponse) {
                            progressBar.setVisibility(View.GONE);
                            if(movieTvResponse == null){
                                Toast.makeText(getActivity(), getResources().getString(R.string.errorFetch), Toast.LENGTH_LONG).show();
                            }
                            else{
                                listData.clear();
                                listData.addAll(movieTvResponse.getListData());
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });


                    RecyclerView rvMovies = getActivity().findViewById(R.id.rv_movies);

                    rvMovies.setHasFixedSize(true);
                    if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    }
                    else {
                        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 4));
                    }
                    adapter = new MoviesTvAdapter(listData, 1);
                    rvMovies.setAdapter(adapter);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    return false;
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        else if(item.getItemId() == R.id.action_setting){
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

