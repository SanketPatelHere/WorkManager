package com.example.workmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    int n = 0;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        n = intent.getExtras().getInt("second");
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        deliverNotification(context);
        //Toast.makeText(context, "Inside Recevier", Toast.LENGTH_SHORT).show();
        Log.i("My Receiver Called ","after "+n+" seconds");
        Toast.makeText(context, "New second = "+n, Toast.LENGTH_SHORT).show();
    }


    private void deliverNotification(Context context)
    {
        Intent contentIntent = new Intent(context, AlarmManagerDemo.class);
        PendingIntent contentPendingIntent  = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand up alert")
                .setContentText("You should stand up and walk around now! "+n+" seconds")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

        //Toast.makeText(context, "1 minute", Toast.LENGTH_SHORT).show();
    }


}