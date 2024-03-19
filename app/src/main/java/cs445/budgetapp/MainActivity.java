package cs445.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cs445.budgetapp.ui.budget.CreateBudgetActivity;
import cs445.budgetapp.ui.login.LoginActivity;
import cs445.budgetapp.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            // User is signed out
                            Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            firebaseAuth.removeAuthStateListener(this); // Remove the listener
                        }
                    }
                });
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_main);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.main_activity_container);

        // Perform item selected listener

        bottomNavigationView.setOnItemReselectedListener(item -> {
            int pageId = item.getItemId();

            if(pageId == R.id.navigation_budget){
                Toast.makeText(this, "Going to budget", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(MainActivity.this, CreateBudgetActivity.class));
            }
            else if(pageId == R.id.navigation_profile) {
                startActivity( new Intent(MainActivity.this, ProfileActivity.class));
                Toast.makeText(this, "Going to profile", Toast.LENGTH_SHORT).show();
            }
            else  {
                Toast.makeText(this, "Already on Home Page!", Toast.LENGTH_SHORT).show();
            }

        });


        MaterialToolbar appBar = findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("budgetSharedPreferences",MODE_PRIVATE);
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currUser != null){
            String[] userArr = currUser.getEmail().split("@");
            String user = userArr[0];
            String income = sharedPreferences.getString(user, "");
            appBar.setTitle("Income: $" + income);

        }

    }
}