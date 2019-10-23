package com.hariz.noah.Reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.hariz.noah.BuildConfig;
import com.hariz.noah.Model.MovieModel;
import com.hariz.noah.Model.Response.MovieResponse;
import com.hariz.noah.Network.RetrofitHelper;
import com.hariz.noah.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpCoomingReminder extends GcmTaskService {
    public static String TAG_TASK_UPCOMING = "upcoming movies";
    String CHANNEL_ID = "channel_02";
    String CHANNEL_NAME = "release_channel";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void loadData() {
        final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        RetrofitHelper.getService().getReleaseToday(BuildConfig.THE_MOVIE_API_TOKEN, today, today)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            List<MovieModel> items = response.body().getResults();
                            int index = new Random().nextInt(items.size());
                            MovieModel item = items.get(index);
                            String title = items.get(index).getTitle();
                            String message = items.get(index).getOverview();
                            int notifId = 200;
                            showNotification(getApplicationContext(), title, message, notifId, item);

                        }

                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
    }
    private void showNotification(Context context, String title, String message, int notifId, MovieModel item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, builder.build());
        }
    }
}
