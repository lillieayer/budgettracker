package cs445.budgetapp;

import android.app.Application;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cs445.budgetapp.data.model.LoggedInUser;

public class MyApplication extends Application {

    private FirebaseDatabase db;

    private LoggedInUser currUser;
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

    public void setCurrUser(LoggedInUser user){
        this.currUser = user;
    }

    public LoggedInUser getCurrUser(){
        return currUser;
    }
}

