package com.example.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.moviecatalogue.viewmodels.SettingViewModel;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Switch swDaily,swRelease;
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
                if(!b){
                     sViewModel.updateReleaseNotification(0);
                }
                else{
                    sViewModel.updateReleaseNotification(1);
                }
            }
        });

        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    sViewModel.updateDailyNotification(0);
                }
                else{
                    sViewModel.updateDailyNotification(1);
                }
            }
        });

    }

}