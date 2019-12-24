package com.example.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BlurWorker extends Worker {
    public BlurWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @Override
    public Result doWork() {
        try {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.img_badminton);
        builder.setContentTitle("Notification Alert, Click Me!");
        builder.setContentText("Hi, This is Android Notification Detail!");
        //builder.build();
            NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel nc = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                nc = new NotificationChannel("aa", "Bbb", NotificationManager.IMPORTANCE_DEFAULT);
                nm.createNotificationChannel(nc);
            }
        //nm.notify();
        nm.notify(1, builder.build());


            /*Notification n  = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    n = new Notification.Builder(getApplicationContext())
                            .setContentTitle("New mail from " + "test@gmail.com")
                            .setContentText("Subject")
                            .setSmallIcon(R.drawable.img_badminton)
                            .setAutoCancel(true).build();
                }


            NotificationManager notificationManager =
                    (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);*/


            Log.i("My Notification2 ", "sent");
            return Result.success();
        } catch (Exception e) {
            Log.i("My Error = ", e + "");
            return Result.failure();
        }

    }

}
