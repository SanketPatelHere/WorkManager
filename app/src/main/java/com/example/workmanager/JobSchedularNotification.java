package com.example.workmanager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class JobSchedularNotification extends AppCompatActivity {
    private static final int JOB_ID = 0;
    private JobScheduler mScheduler;
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_schedular_notification);
        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);
        mSeekBar = findViewById(R.id.seekBar);


        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        }
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    seekBarProgress.setText(progress + " s");
                } else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void scheduleJob(View view) {
        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;

        switch (selectedNetworkID) {
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new JobInfo.Builder(JOB_ID, serviceName)
                    .setRequiredNetworkType(selectedNetworkOption)
                    .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked())
                    .setRequiresCharging(mDeviceChargingSwitch.isChecked());
        }

        if (seekBarSet) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setOverrideDeadline(seekBarInteger * 1000);
            }
        }
        boolean constraintSet = selectedNetworkOption
                != JobInfo.NETWORK_TYPE_NONE
                || mDeviceChargingSwitch.isChecked()
                || mDeviceIdleSwitch.isChecked()
                || seekBarSet;

        if (constraintSet) {
            JobInfo myJobInfo = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                myJobInfo = builder.build();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mScheduler.schedule(myJobInfo);
            }
            Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, "No constraint toast",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * onClick method for cancelling all existing jobs.
     */
    public void cancelJobs(View view) {

        if (mScheduler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mScheduler.cancelAll();
            }
            mScheduler = null;
            Toast.makeText(this, "jobs_canceled", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}