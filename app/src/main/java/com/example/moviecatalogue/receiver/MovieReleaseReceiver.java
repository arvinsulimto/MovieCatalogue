package com.example.moviecatalogue.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;

import com.example.moviecatalogue.models.MovieTvResponse;
import com.example.moviecatalogue.models.MoviesTv;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieReleaseReceiver extends BroadcastReceiver {

    private static final int RELEASE_ALARM_REQUEST_CODE = 1;
    String RELEASE_NOTIFICATION_CHANNEL_ID = "release_notification_id";
    String RELEASE_NOTIFICATION_CHANNEL_NAME = "release_notification";
    String GROUP_KEY_RELEASE = "release_new_movie";
    private List<MoviesTv> listData = new ArrayList<>();
    private PendingIntent pendingIntentRelease;
    private int maxNotif = 2;

    public void setReleaseNotification(Context context){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,2);
        cal.set(Calendar.MINUTE,31);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.AM_PM,Calendar.PM);
        if(cal.getTimeInMillis() > System.currentTimeMillis()) {
            Intent alarmIntent = new Intent(context, MovieReleaseReceiver.class);
            pendingIntentRelease = PendingIntent.getBroadcast(context, RELEASE_ALARM_REQUEST_CODE, alarmIntent, pendingIntentRelease.FLAG_UPDATE_CURRENT);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentRelease);
        }
    }

    public void cancelAlarmRelease(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ALARM_REQUEST_CODE, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        MovieTvResponse PassingData = MovieTvResponse.getInstance();
        listData = PassingData.getListData();
        Intent alarmIntent = new Intent(context, MainActivity.class);
        pendingIntentRelease = PendingIntent.getActivity(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

            if (maxNotif > listData.size()) {
                builder = new NotificationCompat.Builder(context, RELEASE_NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pendingIntentRelease)
                        .setContentTitle(context.getResources().getString(R.string.dailynotification_title))
                        .setContentText(listData.get(listData.size()).getName())
                        .setAutoCancel(true);
            } else {
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                        .setBigContentTitle(listData.size() + context.getResources().getString(R.string.newMovie))
                        .setSummaryText(context.getResources().getString(R.string.newReleaseToday));
                for (int i = 0; i < listData.size(); i++) {
                    inboxStyle.addLine(context.getResources().getString(R.string.newMovies) + listData.get(i).getName());
                }
                builder = new NotificationCompat.Builder(context, RELEASE_NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(listData.size() + context.getResources().getString(R.string.newMovie))
                        .setContentText(context.getResources().getString(R.string.app_name))
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setGroup(GROUP_KEY_RELEASE)
                        .setGroupSummary(true)
                        .setStyle(inboxStyle)
                        .setAutoCancel(true);
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(RELEASE_NOTIFICATION_CHANNEL_ID, RELEASE_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                builder.setChannelId(RELEASE_NOTIFICATION_CHANNEL_ID);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            Notification notification = builder.build();
            if (notificationManager != null) {
                notificationManager.notify(RELEASE_ALARM_REQUEST_CODE, notification);
            }

    }
}
