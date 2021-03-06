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

import java.util.Calendar;


public class DailyNotificationReceiver extends BroadcastReceiver {

    private PendingIntent pendingIntentDaily;
    private static final int DAILY_ALARM_REQUEST_CODE = 0;
    String DAILY_NOTIFICATION_CHANNEL_ID = "daily_notification_id";
    String DAILY_NOTIFICATION_CHANNEL_NAME = "daily_notification";
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent alarmIntent = new Intent(context, MainActivity.class);
        pendingIntentDaily = PendingIntent.getActivity(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, DAILY_NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntentDaily)
                    .setContentTitle(context.getResources().getString(R.string.dailynotification_title))
                    .setContentText(context.getResources().getString(R.string.subTextDailyNotification))
                    .setAutoCancel(true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(DAILY_NOTIFICATION_CHANNEL_ID, DAILY_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                builder.setChannelId(DAILY_NOTIFICATION_CHANNEL_ID);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            Notification notification = builder.build();
            if (notificationManager != null) {
                notificationManager.notify(DAILY_ALARM_REQUEST_CODE, notification);
            }

    }

    public void setDailyNotification(Context context){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,2);
        cal.set(Calendar.MINUTE,31);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.AM_PM,Calendar.PM);
        if(cal.getTimeInMillis() > System.currentTimeMillis()) {
            Intent alarmIntent = new Intent(context, DailyNotificationReceiver.class);
            pendingIntentDaily = PendingIntent.getBroadcast(context, DAILY_ALARM_REQUEST_CODE, alarmIntent, pendingIntentDaily.FLAG_UPDATE_CURRENT);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentDaily);
        }
    }

    public void cancelAlarmDaily(Context context) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DailyNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_ALARM_REQUEST_CODE, intent, 0);
            pendingIntent.cancel();
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
    }

}
