package com.hariz.noah;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.hariz.noah.Reminder.DailyReminder;
import com.hariz.noah.Reminder.UpCoomingTask;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderActivity extends AppCompatActivity {

    private Switch dailySwitch, upcomingSwitch;
    private UpCoomingTask mUpComingTask;
    private DailyReminder dailyReminderMovie;
    private boolean isUpcoming, isDaily;
    private Preference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        dailySwitch = findViewById(R.id.switch_daily);
        upcomingSwitch = findViewById(R.id.switch_upcoming);
        dailyReminderMovie = new DailyReminder();
        appPreference = new Preference(this);
        setEnableDisableNotif();
    }

    private void setEnableDisableNotif() {
        if (appPreference.isDaily()) {
            dailySwitch.setChecked(true);
        } else {
            dailySwitch.setChecked(false);
        }

        if (appPreference.isUpcoming()) {
            upcomingSwitch.setChecked(true);
        } else {
            upcomingSwitch.setChecked(false);
        }
    }

    @OnClick({R.id.switch_daily, R.id.switch_upcoming, R.id.setting_local})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_daily:
                isDaily = dailySwitch.isChecked();
                if (isDaily) {
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(isDaily);
                    dailyReminderMovie.setRepeatingAlarm(this, DailyReminder.TYPE_REPEATING,
                            "07:00", "Reminder Movie Today");
                } else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    dailyReminderMovie.cancelAlarm(this, DailyReminder.TYPE_REPEATING);
                }
                break;
            case R.id.switch_upcoming:
                mUpComingTask = new UpCoomingTask(this);
                isUpcoming = upcomingSwitch.isChecked();
                if (isUpcoming) {
                    upcomingSwitch.setEnabled(true);
                    appPreference.setUpcoming(isUpcoming);

                    mUpComingTask.createPeriodicTask();
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                } else {
                    upcomingSwitch.setChecked(false);
                    appPreference.setUpcoming(isUpcoming);
                    mUpComingTask.cancelPeriodicTask();
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting_local:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }
}
