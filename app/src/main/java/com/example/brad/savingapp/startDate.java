package com.example.brad.savingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class startDate {

    private int day;
    private int month;
    private int year;

    public startDate(String dd, String mm, String yyyy){
        day = Integer.parseInt(dd.toString());
        month = Integer.parseInt(mm.toString());
        year = Integer.parseInt(yyyy.toString());
    }

    public long dateConversion(){

        String monthString = new SimpleDateFormat("LLL").format(new Date(year, month-1, day));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dateInString = day+"-"+monthString+"-"+year;
        long millis = 0;

        try {
            Date dateParse = formatter.parse(dateInString);
            millis = dateParse.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return millis;
    }

}
