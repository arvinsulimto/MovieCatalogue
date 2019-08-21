package com.example.moviecatalogue.repositories.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Binder;

import com.example.moviecatalogue.models.MoviesTv;
import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private Context mContext;

    public DatabaseAccess(Context context)
    {
        this.openHelper = new DatabaseHelper(context);
        mContext = context;
    }


    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }

    }


    public ArrayList<MoviesTv> getAllFavourite(){
        open();
        ArrayList<MoviesTv> data = new ArrayList<>();
        final long identityToken = Binder.clearCallingIdentity();

        Cursor c = db.rawQuery("SELECT * FROM Catalogue",null);
        Binder.restoreCallingIdentity(identityToken);
        if(c.moveToFirst()){
            do{
                MoviesTv all = new MoviesTv();
                all.setName(c.getString(c.getColumnIndex("Name")));
                all.setRating(c.getFloat(c.getColumnIndex("Rate")));
                all.setOverview(c.getString(c.getColumnIndex("Description")));
                all.setPoster(c.getString(c.getColumnIndex("Poster")));
                data.add(all);
            }while(c.moveToNext());
        }

        close();
        return data;
    }


    public ArrayList<MoviesTv> getFavouriteMovie(){
        open();
        ArrayList<MoviesTv> data = new ArrayList<MoviesTv>();
        Cursor c = db.rawQuery("SELECT * FROM Catalogue WHERE Type = 1",null);
        if(c.moveToFirst()){
            do{
                MoviesTv movie = new MoviesTv();
                movie.setName(c.getString(c.getColumnIndex("Name")));
                movie.setRating(c.getFloat(c.getColumnIndex("Rate")));
                movie.setOverview(c.getString(c.getColumnIndex("Description")));
                movie.setPoster(c.getString(c.getColumnIndex("Poster")));
                data.add(movie);
            }while(c.moveToNext());
        }
        close();
        return data;
    }

    public ArrayList<MoviesTv> getFavouriteTvShow(){
        open();
        ArrayList<MoviesTv> data = new ArrayList<MoviesTv>();

        Cursor c = db.rawQuery("SELECT * FROM Catalogue WHERE Type = 2",null);
        if(c.moveToFirst()){
            do{
                MoviesTv tv = new MoviesTv();
                tv.setTvName(c.getString(c.getColumnIndex("Name")));
                tv.setRating(c.getFloat(c.getColumnIndex("Rate")));
                tv.setOverview(c.getString(c.getColumnIndex("Description")));
                tv.setPoster(c.getString(c.getColumnIndex("Poster")));
                data.add(tv);

            }while(c.moveToNext());
        }
        close();
        return data;
    }

    public Boolean getCatalogueLoveOrNot(String catalogueName){
        open();
        Cursor c =db.rawQuery("SELECT * FROM Catalogue WHERE Name="+"'"+catalogueName+"'",new String[]{});

        if(c.getCount() > 0){
            close();
            return true;
        }
        else{
            close();
            return false;
        }

    }

    public Boolean insertLove(String name,float rate,String description,String poster,int type){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Rate",rate);
        contentValues.put("Description",description);
        contentValues.put("Poster",poster);
        contentValues.put("Type",type);
        long ins = db.insert("Catalogue",null,contentValues);
        if(ins==-1){
            close();
            return false;
        }
        else{
            close();
            return true;
        }
    }

    public Boolean deleteLove(String name){
        open();
        db.execSQL("DELETE FROM Catalogue WHERE Name ="+"'"+name+"'");
        close();
        return true;
    }






}
