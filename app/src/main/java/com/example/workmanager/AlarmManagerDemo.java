package com.example.workmanager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class AlarmManagerDemo extends AppCompatActivity {
    ToggleButton alarmToggle;
    TextView tvCounter;
    EditText etAlarm;
    Button btnStart;
    int counter = 0;
    long repeatInterval;
    long triggerTime;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    //for counter
    long startTime;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager_demo);

        //int i;
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        alarmToggle = (ToggleButton)findViewById(R.id.alarmToggle );
        tvCounter = (TextView)findViewById(R.id.tvCounter );
        etAlarm = (EditText)findViewById(R.id.etAlarm );
        btnStart = (Button) findViewById(R.id.btnStart );
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for second method
                int i = Integer.parseInt(etAlarm.getText().toString());
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("second",i);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent,0);
                AlarmManager a2 = (AlarmManager)getSystemService(ALARM_SERVICE);
                a2.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(i*1000), pendingIntent);
                Toast.makeText(AlarmManagerDemo.this, "Alarm set in "+i+" seconds", Toast.LENGTH_SHORT).show();

            }
        });
        //alarm manager use
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);


        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMesage = "";
                if(isChecked)
                {
                    //deliverNotification(AlarmManagerDemo.this);

                    //for alarm manager = after every 15 minutes
                    //repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    repeatInterval = 1 * 10 * 1000;  //10 seconds
                    triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
                    if(alarmManager!=null)
                    {
                        /*alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime,
                                repeatInterval, notifyPendingIntent);*/
                        Log.i("My alarm set",repeatInterval+" "+triggerTime);
                    }
                    counter++;


                    startTime = System.currentTimeMillis();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            long diff = System.currentTimeMillis() - startTime;
                            if((diff/1000)==10)
                            {
                                startTime = System.currentTimeMillis();
                            }
                            tvCounter.setText("Counter on = "+Long.toString(diff / 1000));
                            handler.postDelayed(this, 1000);
                        }
                    });
                    //tvCounter.setText("Counter = "+counter);
                    toastMesage = "Stand up Alarm on!";





                }
                else
                {
                    mNotificationManager.cancelAll();
                    if(alarmManager!=null)
                    {
                        alarmManager.cancel(notifyPendingIntent);
                        Log.i("My alarm cancel at",repeatInterval+" "+triggerTime);
                    }
                    tvCounter.setText("Counter off = ");
                    startTime = System.currentTimeMillis();
                    toastMesage = "Stand up Alarm off!";
                }

                Toast.makeText(AlarmManagerDemo.this, toastMesage, Toast.LENGTH_SHORT).show();
            }
        });

        //for notification every 15 minutes
        createNotificationChannel();



    }
    public void createNotificationChannel()
    {
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH);
            nc.enableLights(true);
            nc.setLightColor(Color.RED);
            nc.enableVibration(true);
            nc.setDescription("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(nc);
        }

    }

}
