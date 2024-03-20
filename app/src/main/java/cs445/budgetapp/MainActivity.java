package cs445.budgetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;


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

import java.io.IOException;
import java.security.GeneralSecurityException;

import cs445.budgetapp.ui.budget.CreateBudgetActivity;
import cs445.budgetapp.ui.login.LoginActivity;
import cs445.budgetapp.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton logoutButton = findViewById(R.id.logout_button_main);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                mAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
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


        MaterialToolbar appBar = findViewById(R.id.toolbar_main);
        MasterKey masterKey = null;
        try {
            masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    this,
                    "budgetSharedPreferences",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FirebaseUser currUser = mAuth.getCurrentUser();
        if (currUser != null){
            // eliminate poor regex
            String userId = currUser.getUid();
            String income = sharedPreferences.getString(userId, "");
            appBar.setTitle("Income: $" + income);

        }

    }
}