package cs445.budgetapp;

import android.app.Application;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import cs445.budgetapp.data.model.LoggedInUser;

public class MyApplication extends Application {

    private FirebaseDatabase db;
    private static int startMonth;

    private static int startDay;
    private static int startYear;
    private static int endMonth;
    private static int endDay;
    private static int endYear;


    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your global resources here
        db = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);
    }

    public FirebaseDatabase getDb() {
        return db;
    }

    public static void setStartMonth(int month){
        startMonth = month;
    }
    public static void setStartDay(int day){
        startDay = day;
    }
    public static void setStartYear(int year){
        startYear= year;
    }

    public static void setEndMonth(int month){
        endMonth = month;
    }
    public static void setEndDay(int day){
        endDay = day;
    }
    public static void setEndYear(int year){
        endYear = year;
    }


    public static int getStartMonth(){
        return startMonth;
    }
    public static int getStartDay(){
        return startDay;
    }
    public static int getStartYear(){
        return startYear;
    }

    public static int getEndMonth(){
        return endMonth;
    }
    public static int getEndDay(){
        return endDay;
    }
    public static int getEndYear(){
        return endYear;
    }

}

