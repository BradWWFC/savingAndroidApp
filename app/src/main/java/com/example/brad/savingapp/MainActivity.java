package com.example.brad.savingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private boolean Activated = false;
    private SharedPreferences prefs;

    public void newActivity(){
        SharedPreferences sharedPref = getSharedPreferences("Activated", Context.MODE_PRIVATE);
        Boolean x = sharedPref.getBoolean("Activated",false);
        if (x) {
            startActivity(new Intent(MainActivity.this, Other.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newActivity();
    }

    public long getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return cal.getTimeInMillis();
    }

    public void Start(View v){
        Intent Intent = new Intent(MainActivity.this, Other.class);
        startActivity(Intent);
        Activated = true;

        long todayMillis2 = getTodaysDate();

        SharedPreferences sharedPref = getSharedPreferences("Activated", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Activated",true).putLong("DateStart",todayMillis2).putLong("DateUpdate",todayMillis2);
        editor.apply();
}


}