package cs445.budgetapp;

import android.app.Application;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    DatabaseReference db;
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your global resources here
        db = FirebaseDatabase.getInstance().getReference();
        FirebaseApp.initializeApp(this);
    }
}

