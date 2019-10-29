package com.hariz.noah.Reminder;

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
import android.widget.Toast;

import com.hariz.noah.BuildConfig;
import com.hariz.noah.MainActivity;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Model.Response.MovieResponse;
import com.hariz.noah.Network.RetrofitHelper;
import com.hariz.noah.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpCoomingReminder extends BroadcastReceiver {
    public final static int NOTIFICATION_ID_ = 502;
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    private static final CharSequence CHANNEL_NAME = "dicoding channel";
    private static final int MAX_NOTIFICATION = 2;
    public List<MovieModel> listMovie = new ArrayList<>();
    private int idNotification = 0;

    public UpCoomingReminder() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        RetrofitHelper.getService().getReleaseToday(BuildConfig.THE_MOVIE_API_TOKEN, today, today)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            listMovie = response.body().getResults();
                            List<MovieModel> items = response.body().getResults();
                            int index = new Random().nextInt(items.size());
                            MovieModel item = items.get(index);
                            idNotification++;
                            String title = items.get(index).getTitle();
                            String message = items.get(index).getOverview();
                            sendNotification(context, title, message, idNotification);
                        }

                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });

    }

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_48px);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;

        //Melakukan pengecekan jika idNotification lebih kecil dari Max Notif
        String CHANNEL_ID = "channel_01";
        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("" + listMovie.get(idNotification).getOriginalTitle())
                    .setContentText(listMovie.get(idNotification).getOverview())
                    .setSmallIcon(R.drawable.ic_local_movies)
//                    .setLargeIcon(largeIcon)
                    .setGroup(EXTRA_MESSAGE_RECIEVE)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(listMovie.get(idNotification).getOriginalTitle())
                    .addLine(listMovie.get(idNotification - 1).getOriginalTitle())
                    .setBigContentTitle(idNotification + " new movie")
                    .setSummaryText("Nonton AH");
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(idNotification + " new movie")
//                    .setContentText("mail@dicoding.com")
                    .setSmallIcon(R.drawable.ic_local_movies)
                    .setGroup(EXTRA_MESSAGE_RECIEVE)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }
         /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(idNotification, notification);
        }
    }


//
//    private void sendNotification(Context context, String title, String desc, int id) {
////        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_48px);
//////        Intent intent = new Intent(this, UpCoomingReminder.class);
//////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//////        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        NotificationCompat.Builder mBuilder;
//
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
//                Context.NOTIFICATION_SERVICE);
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        String CHANNEL_ID = "Channel_01";
//        String CHANNEL_NAME = "release_channel";
//        if (notifId < MAX_NOTIFICATION){
//
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_local_movies)
//                .setContentTitle(title)
//                .setContentText(desc)
//                .setContentIntent(pendingIntent)
//                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .setAutoCancel(true)
//                .setSound(uriTone);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                    CHANNEL_NAME,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
//            builder.setChannelId(CHANNEL_ID);
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//        if (notificationManager != null) {
//            notificationManager.notify(id, builder.build());
//        }
//    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpCoomingReminder.class);
        intent.putExtra(EXTRA_MESSAGE_RECIEVE, message);
        intent.putExtra(EXTRA_TYPE_RECIEVE, type);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpCoomingReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show();
    }

}
