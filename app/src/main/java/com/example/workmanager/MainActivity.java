package com.example.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MainActivity.this, BlurWorker.class);
                
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(BlurWorker.class)
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        .build();
                WorkManager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest);
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChargerConnect.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "btn2", Toast.LENGTH_SHORT).show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlarmManagerDemo.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "btn2", Toast.LENGTH_SHORT).show();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, JobSchedularNotification.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "btn2", Toast.LENGTH_SHORT).show();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SPDemo.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "btn2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
