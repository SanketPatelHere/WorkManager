package com.example.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class batteryChangeService extends Service {




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        ifilter.addAction(Intent.ACTION_UMS_CONNECTED);
        ifilter.addAction(Intent.ACTION_UMS_DISCONNECTED);
        ifilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        ifilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        //ifilter.addAction(Intent.);

        registerReceiver(mBatteryStateReceiver, ifilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        startForeground(11, sendNotification("aaa","bbb"));
        //stopForeground(true);
        return START_NOT_STICKY;
    }
    private BroadcastReceiver mBatteryStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(context, "Sanket ~ The device is charging", Toast.LENGTH_SHORT).show();
            }
            else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
                Toast.makeText(context, "Sanket ~ The device is not charging", Toast.LENGTH_SHORT).show();
                //code for mediaplayer for music 
            }
            //not working this = usb connect - disconnect
            else if (intent.getAction().equals(Intent.ACTION_UMS_DISCONNECTED)) {
                Toast.makeText(context, "The ums is disconnected1", Toast.LENGTH_SHORT).show();
            }

            else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED))
            {
                Toast.makeText(context, "The usb is attached", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onDestroy() {
        unregisterReceiver(mBatteryStateReceiver);
    }

    public Notification sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

//If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        //notificationManager.notify(1, notification.build());
        return notification.build();
    }
}