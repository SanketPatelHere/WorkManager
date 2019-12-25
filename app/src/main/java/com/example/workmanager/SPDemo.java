package com.example.workmanager;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SPDemo extends AppCompatActivity {
    private int mCount = 0;
    private TextView mShowCount;
    private int mColor;
    private TextView mShowCountTextView;
    private final String COUNT_KEY = "count";
    private final String COLOR_KEY = "color";
    SharedPreferences sp;
    String spFile = "CounterPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spdemo);
        mShowCountTextView = findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(this,R.color.colorPrimaryDark);
        sp = getSharedPreferences(spFile, MODE_PRIVATE);


        mCount = sp.getInt(COUNT_KEY, 0);
        mShowCountTextView.setText(String.format("%s", mCount));
        mColor = sp.getInt(COLOR_KEY, mColor);
        mShowCountTextView.setBackgroundColor(mColor);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(COUNT_KEY, mCount).commit();
        editor.putInt(COLOR_KEY, mColor).commit();
        //editor.apply();  //or commit();
    }
    public void changeBackground(View view)
    {
        int color = ((ColorDrawable)view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }
    public void countUp()
    {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }
    public void reset()
    {
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this, R.color.colorPrimary);
        mShowCountTextView.setBackgroundColor(mColor);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        //editor.apply();
    }
}
