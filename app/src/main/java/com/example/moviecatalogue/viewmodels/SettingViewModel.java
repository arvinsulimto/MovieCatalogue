package com.example.moviecatalogue.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.moviecatalogue.repositories.database.DatabaseAccess;

public class SettingViewModel extends ViewModel {

    private Context context;
    private DatabaseAccess databaseAccess;
    public SettingViewModel(Context context){
        this.context = context;
        databaseAccess = new DatabaseAccess(context);
    }

    public int checkDailyNotification(){
        int daily = databaseAccess.checkDailyNotification();
        return daily;
    }

    public int checkReleaseNotification(){
        int release = databaseAccess.checkReleaseNotification();
        return release;
    }

    public Boolean updateDailyNotification(int value){
        Boolean daily = databaseAccess.updateDailyNotification(value);
        return daily;
    }

    public Boolean updateReleaseNotification(int value){
        Boolean reminder = databaseAccess.updateReleaseNotification(value);
        return reminder;
    }
}
