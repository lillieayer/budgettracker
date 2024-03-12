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

    private FirebaseUser currUser;
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your global resources here
        db = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);
        currUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseDatabase getDb() {
        return db;
    }
    public FirebaseUser getAuthUser(){
        return currUser;
    }
}

