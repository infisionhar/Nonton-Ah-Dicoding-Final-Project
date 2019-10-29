package com.hariz.noah.Reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.hariz.noah.MainActivity;
import com.hariz.noah.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {
    public final static int NOTIFICATION_ID = 501;
    public static final String EXTRA_MESSAGE_PREF = "message";
    public static final String EXTRA_TYPE_PREF = "type";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context, context.getResources().getString(R.string.app_name),
                intent.getStringExtra(EXTRA_MESSAGE_PREF), NOTIFICATION_ID);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "Channel_02";
        String CHANNEL_NAME = "daily_channel";
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, desc)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(uriTone);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }

    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_MESSAGE_PREF, message);
        intent.putExtra(EXTRA_TYPE_PREF, type);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        alarmManager.cancel(pendingIntent);

    }

}
