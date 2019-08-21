package com.example.moviecatalogue.viewpager;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.fragment.FavouriteMovieFragment;
import com.example.moviecatalogue.fragment.FavouriteTvFragment;

public class ViewPagerFavouriteAdapter extends FragmentStatePagerAdapter {


    private int tabCount;
    private Context context;
    public ViewPagerFavouriteAdapter(FragmentManager fm,int tabCount,Context context) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                FavouriteMovieFragment movie = new FavouriteMovieFragment();
                return movie;
            case 1 :
                FavouriteTvFragment tv = new FavouriteTvFragment();
                return tv;

             default:
              return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        switch(position){
            case 0 :
                return context.getResources().getString(R.string.title_movie);
            case 1 :
                return context.getResources().getString(R.string.title_tv_show);
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
