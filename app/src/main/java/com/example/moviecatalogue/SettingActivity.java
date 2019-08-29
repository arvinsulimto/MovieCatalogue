package com.example.moviecatalogue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.moviecatalogue.models.MovieTvResponse;
import com.example.moviecatalogue.models.MoviesTv;
import com.example.moviecatalogue.receiver.DailyNotificationReceiver;
import com.example.moviecatalogue.receiver.MovieReleaseReceiver;
import com.example.moviecatalogue.viewmodels.MovieViewModel;
import com.example.moviecatalogue.viewmodels.SettingViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Switch swDaily,swRelease;
    MovieViewModel mViewModel;
    private ArrayList<MoviesTv> stackNotif = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swRelease = findViewById(R.id.swRelease);
        swDaily = findViewById(R.id.swDaily);
        final SettingViewModel sViewModel = new SettingViewModel(getApplicationContext());
        int daily = sViewModel.checkDailyNotification();
        int release = sViewModel.checkReleaseNotification();
        if(daily == 0){
            swDaily.setChecked(false);
        }
        if(release == 0){
            swRelease.setChecked(false);
        }
        if(daily == 1){
            swDaily.setChecked(true);
        }
        if(release == 1){
            swRelease.setChecked(true);
        }

        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MovieReleaseReceiver movieReleaseReceiver = new MovieReleaseReceiver();
                if(!b){
                    movieReleaseReceiver.cancelAlarmRelease(getApplicationContext());
                     sViewModel.updateReleaseNotification(0);
                }
                else{
                    setNotificationForRelease();
                    movieReleaseReceiver.setReleaseNotification(getApplicationContext());
                    sViewModel.updateReleaseNotification(1);
                }
            }
        });

        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DailyNotificationReceiver dailyNotificationReceiver = new DailyNotificationReceiver();
                if(!b){
                    dailyNotificationReceiver.cancelAlarmDaily(getApplicationContext());
                    sViewModel.updateDailyNotification(0);
                }
                else{
                    dailyNotificationReceiver.setDailyNotification(getApplicationContext());
                    sViewModel.updateDailyNotification(1);
                }
            }
        });

    }

    public void setNotificationForRelease(){
        mViewModel =  ViewModelProviders.of(this).get(MovieViewModel.class);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        mViewModel.getNowMovie(formattedDate,formattedDate);
        mViewModel.getNowMovieByDate().observe(this, new Observer<MovieTvResponse>() {
            @Override
            public void onChanged(@Nullable MovieTvResponse movieTvResponse) {
                if(movieTvResponse == null){
                    Toast.makeText(SettingActivity.this, getResources().getString(R.string.errorFetch), Toast.LENGTH_SHORT).show();
                }
                else{
                    stackNotif.clear();
                    stackNotif.addAll(movieTvResponse.getListData());
                    MovieTvResponse PassingData = MovieTvResponse.getInstance();
                    PassingData.setDataMovie(stackNotif);
                }
            }
        });
    }



}