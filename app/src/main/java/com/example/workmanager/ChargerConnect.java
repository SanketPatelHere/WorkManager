package com.example.workmanager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ChargerConnect extends AppCompatActivity {
    Button btnSendBroadcast;
    private CustomReceiver mReceiver, mReceiver2;

    //for custom broadcast
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger_connect);
        btnSendBroadcast = (Button)findViewById(R.id.btnSendBroadcast);  //not use this button
        mReceiver = new CustomReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);

        //this.registerReceiver(mReceiver, filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(ChargerConnect.this, batteryChangeService.class));
        }


        //for custom broadcast receiver
        /*mReceiver2 = new CustomReceiver();
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver2, new IntentFilter(ACTION_CUSTOM_BROADCAST));
        Toast.makeText(this, "last", Toast.LENGTH_SHORT).show();*/

    }

    @Override
    protected void onDestroy() {
        //this.unregisterReceiver(mReceiver2);  //no need this = becuase of manifest file declare

        //for custom broacast receiver
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
