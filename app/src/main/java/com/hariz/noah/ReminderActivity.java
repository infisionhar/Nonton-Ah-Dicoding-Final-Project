package com.hariz.noah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.hariz.noah.Reminder.DailyReminder;
import com.hariz.noah.Reminder.UpCoomingPreference;
import com.hariz.noah.Reminder.UpCoomingReminder;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReminderActivity extends AppCompatActivity {
    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String TYPE_REMINDER_RECIEVE = "reminderAlarmRelease";
    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";
    public DailyReminder dailyReceiver;
    public UpCoomingReminder movieReleaseReceiver;
    public UpCoomingPreference notificationPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;
    Switch dailyReminder, releaseReminder;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        unbinder = ButterKnife.bind(this);

        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
        dailyReminder = findViewById(R.id.switch_daily);
        boolean checkDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        dailyReminder.setChecked(checkDailyReminder);
        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorDailyReminder = sDailyReminder.edit();
                if (isChecked) {
                    editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, true);
                    editorDailyReminder.apply();
                    dailyReminderOn();
                } else {
                    editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, false);
                    editorDailyReminder.commit();
                    dailyReminderOff();
                }
            }
        });
        sReleaseReminder = getSharedPreferences(KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        releaseReminder = findViewById(R.id.switch_upcoming);
        boolean checkUpcomingReminder = sReleaseReminder.getBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
        releaseReminder.setChecked(checkUpcomingReminder);
        releaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorReleaseReminder = sReleaseReminder.edit();
                if (isChecked) {
                    editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, true);
                    editorReleaseReminder.apply();
                    releaseReminderOn();
                } else {
                    editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
                    editorReleaseReminder.commit();
                    releaseReminderOff();
                }
            }
        });
        movieReleaseReceiver = new UpCoomingReminder();
        dailyReceiver = new DailyReminder();
        notificationPreference = new UpCoomingPreference(this);
        setPreference();
    }

    private void releaseReminderOn() {
        String time = "08:00";
        String message = getResources().getString(R.string.app_name_);
        notificationPreference.setReminderReleaseTime(time);
        notificationPreference.setReminderReleaseMessage(message);
        movieReleaseReceiver.setAlarm(ReminderActivity.this, TYPE_REMINDER_PREF, time, message);

        Toast.makeText(this, "Aktif", Toast.LENGTH_SHORT).show();
    }

    private void releaseReminderOff() {
        movieReleaseReceiver.cancelAlarm(ReminderActivity.this);

        Toast.makeText(this, "Mati", Toast.LENGTH_SHORT).show();
    }

    private void dailyReminderOn() {
        String time = "07:00";
        String message = getResources().getString(R.string.app_name_);
        notificationPreference.setReminderDailyTime(time);
        notificationPreference.setReminderDailyMessage(message);
        dailyReceiver.setAlarm(ReminderActivity.this, TYPE_REMINDER_RECIEVE, time, message);
        Toast.makeText(this, "Aktif", Toast.LENGTH_SHORT).show();
    }

    private void dailyReminderOff() {
        dailyReceiver.cancelAlarm(ReminderActivity.this);
        Toast.makeText(this, "Mati", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.setting_local)
    public void onClick(View view) {
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }


    private void setPreference() {


    }
}
