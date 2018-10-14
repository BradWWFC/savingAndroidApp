package com.example.brad.savingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Other extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    private static final String TAG = "MyActivity";
    private String dialogDay;
    private String dialogMonth;
    private String dialogYear;

    public long getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

        return cal.getTimeInMillis();
    }

    public int daysFromStart(Date dateParameter) {
        SharedPreferences pref = getSharedPreferences("Activated", Context.MODE_PRIVATE);
        Date startDate = new Date(pref.getLong("DateStart", 101802177));
        Date currentDate = dateParameter;

        long days = currentDate.getTime() - startDate.getTime();
        int diff = (int) TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);

        return diff;
    }

    public Date longToDate(long x) {
        return new Date(x);
    }

    public Double moneySaved(Double date){
        double total = 0;
        double x = date;
        for(int i = 0; i<x;i++){
            total += (i + 1);
        }
        return total/100;
    }

    public Double moneyToBeSaved(){
        long todaysDateLong = getTodaysDate();
        Date todaysDate = longToDate(todaysDateLong);
        double diff = (double) daysFromStart(todaysDate);
        double saved = moneySaved(diff);

        SharedPreferences pref = getSharedPreferences("Activated", Context.MODE_PRIVATE);
        Date recentDate = new Date(pref.getLong("DateUpdate", 1018021774496l));
        double diff2 = (double) daysFromStart(recentDate);
        double saved2 = moneySaved(diff2);

        return saved - saved2;
    }

    public void Update(View v){
        long todayMillis2 = getTodaysDate();
        SharedPreferences pref = getSharedPreferences("Activated", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("DateUpdate",todayMillis2);
        editor.apply();

        TextView total = findViewById(R.id.TotalAmount);
        TextView total2 = findViewById(R.id.AmountToAdd);
        long todaysDateLong = getTodaysDate();
        Date todaysDate = longToDate(todaysDateLong);
        double diff = (double) daysFromStart(todaysDate);
        DecimalFormat df = new DecimalFormat("0.00##");
        total.setText("£" + df.format(moneySaved(diff)));
        total2.setText("£" + df.format(moneyToBeSaved()));

        //FOR CHECKING STUFF, DELETE WHEN FINISHED

        String x = String.valueOf(pref.getLong("DateUpdate",000000000));
        String y = String.valueOf(pref.getLong("DateStart",000000000));
        Toast.makeText(Other.this,x,Toast.LENGTH_SHORT).show();
        Log.i(TAG,x);
        Log.i(TAG,y);
    }

    @Override
    public void applyTexts(String day, String month, String year) {
        dialogDay = day;
        dialogMonth = month;
        dialogYear = year;

        updateDate();
        finish();
        startActivity(getIntent());
    }

    public void updateDate(){

        startDate date = new startDate(dialogDay, dialogMonth, dialogYear);

        long newDate = date.dateConversion();

        SharedPreferences pref = getSharedPreferences("Activated", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("DateStart",newDate);
        editor.apply();


    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        TextView total = findViewById(R.id.TotalAmount);
        TextView total2 = findViewById(R.id.AmountToAdd);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button button = (Button) myToolbar.findViewById(R.id.changeDate);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        long todaysDateLong = getTodaysDate();
        Date todaysDate = longToDate(todaysDateLong);
        double startDay = (double) daysFromStart(todaysDate);

        DecimalFormat df = new DecimalFormat("0.00##");
        total.setText("£" + df.format(moneySaved(startDay)));
        total2.setText("£" + df.format(moneyToBeSaved()));
    }


}
