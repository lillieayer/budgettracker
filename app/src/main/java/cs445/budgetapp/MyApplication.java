package cs445.budgetapp;

import android.app.Application;

import androidx.room.Room;

import cs445.budgetapp.database.AppDatabase;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your global resources here

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my-database").build();
    }
}

