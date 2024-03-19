package cs445.budgetapp;

import android.app.Application;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cs445.budgetapp.data.model.LoggedInUser;

public class MyApplication extends Application {

    private FirebaseDatabase db;

    private LoggedInUser user;


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

    public LoggedInUser getUserDisplayName(){return user;}
    public void setUserDisplayName(LoggedInUser current){user=current;}
}

