package com.hariz.noah.Reminder;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class UpCoomingTask {
    private GcmNetworkManager mGcmNetworkManager;

    public UpCoomingTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(UpCoomingReminder.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(UpCoomingReminder.TAG_TASK_UPCOMING)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(UpCoomingReminder.TAG_TASK_UPCOMING, UpCoomingReminder.class);
        }
    }
}
