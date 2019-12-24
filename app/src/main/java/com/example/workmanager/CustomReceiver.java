package com.example.workmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    //for custom broadcast
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if(intentAction!=null)
        {
            String toastMessage = "Unknown intent action";
            switch (intentAction)
            {
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast Received";
                    break;
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power Conncted!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power Disconnected";
                    break;
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
