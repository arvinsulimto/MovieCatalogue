package com.example.moviecatalogue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.moviecatalogue.fragment.FavouriteFragment;
import com.example.moviecatalogue.fragment.MovieFragment;
import com.example.moviecatalogue.fragment.TvFragment;
import com.example.moviecatalogue.models.MovieTvResponse;
import com.example.moviecatalogue.models.MoviesTv;
import com.example.moviecatalogue.receiver.DailyNotificationReceiver;
import com.example.moviecatalogue.receiver.MovieReleaseReceiver;
import com.example.moviecatalogue.viewmodels.MovieViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private PendingIntent pendingIntentDaily;
    private static final int DAILY_ALARM_REQUEST_CODE = 0;
    private PendingIntent pendingIntentRelease;
    private static final int RELEASE_ALARM_REQUEST_CODE = 1;
    MovieViewModel mViewModel;
    private ArrayList<MoviesTv> stackNotif = new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tv_show:
                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorit :
                    fragment = new FavouriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        setDailyNotification();
        setMovieReleaseNotification();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null){
            navView.setSelectedItemId(R.id.navigation_movie);
        }
    }

    private void setMovieReleaseNotification() {
            mViewModel =  ViewModelProviders.of(this).get(MovieViewModel.class);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);
            mViewModel.getNowMovie(formattedDate,formattedDate);
            mViewModel.getNowMovieByDate().observe(this, new Observer<MovieTvResponse>() {
            @Override
            public void onChanged(@Nullable MovieTvResponse movieTvResponse) {
                if(movieTvResponse == null){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.errorFetch), Toast.LENGTH_SHORT).show();
                }
                else{
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY,8);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    cal.set(Calendar.AM_PM,Calendar.AM);
                    if(cal.getTimeInMillis() > System.currentTimeMillis()) {
                            stackNotif.clear();
                            stackNotif.addAll(movieTvResponse.getListData());
                            Intent alarmIntent = new Intent(MainActivity.this, MovieReleaseReceiver.class);
                            MovieTvResponse PassingData = MovieTvResponse.getInstance();
                            PassingData.setDataMovie(stackNotif);
                            pendingIntentRelease = PendingIntent.getBroadcast(MainActivity.this, RELEASE_ALARM_REQUEST_CODE, alarmIntent, pendingIntentRelease.FLAG_UPDATE_CURRENT);
                            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()/1000, AlarmManager.INTERVAL_DAY, pendingIntentRelease);
                        }
                }
            }
        });

    }

    private void setDailyNotification(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,7);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.AM_PM,Calendar.AM);

        if(cal.getTimeInMillis() > System.currentTimeMillis()) {
            Intent alarmIntent = new Intent(this, DailyNotificationReceiver.class);
            pendingIntentDaily = PendingIntent.getBroadcast(this, DAILY_ALARM_REQUEST_CODE, alarmIntent, pendingIntentDaily.FLAG_UPDATE_CURRENT);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()/1000,AlarmManager.INTERVAL_DAY, pendingIntentDaily);
        }

    }

}
