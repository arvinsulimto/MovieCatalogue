package com.example.moviecatalogue.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.moviecatalogue.models.MoviesTv;
import com.example.moviecatalogue.repositories.database.DatabaseAccess;

import java.util.ArrayList;

public class FavouriteViewModel extends ViewModel {


    private Context context;
    private DatabaseAccess databaseAccess;
    public FavouriteViewModel(Context context){
        this.context = context;
        databaseAccess = new DatabaseAccess(context);
    }


    public ArrayList<MoviesTv> getFavouriteMovie(){
        ArrayList<MoviesTv> data;
        data = databaseAccess.getFavouriteMovie();
        return data;
    }

    public ArrayList<MoviesTv> getFavouriteTvShow(){
        ArrayList<MoviesTv> data;
        data = databaseAccess.getFavouriteTvShow();
        return data;
    }

    public Boolean getCatalogueLoveOrNot(String catalogueName){
        boolean validateData = databaseAccess.getCatalogueLoveOrNot(catalogueName);
        return validateData;
    }

    public Boolean insertLove(String name,float rate,String description,String poster,int type){
        Boolean insertData = databaseAccess.insertLove(name, rate, description, poster, type);
        return insertData;
    }

    public Boolean deleteLove(String name){
        return databaseAccess.deleteLove(name);
    }

    public ArrayList<MoviesTv> getAllFavourite(){
        ArrayList<MoviesTv> data;
        data = databaseAccess.getAllFavourite();
        return data;
    }




}
