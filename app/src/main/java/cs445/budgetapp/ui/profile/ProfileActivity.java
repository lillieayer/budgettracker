package cs445.budgetapp.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import cs445.budgetapp.MainActivity;
import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;
import cs445.budgetapp.ui.budget.Budget;
import cs445.budgetapp.ui.budget.CreateBudgetActivity;
import cs445.budgetapp.ui.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText createIncome = findViewById(R.id.editIncome);
        ImageButton deleteButton = findViewById(R.id.deleteButton);

        List<Budget> budgetList = new ArrayList<>();
        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(budgetAdapter);

        Button saveButton = findViewById(R.id.saveButton);
        ImageButton editProfile = findViewById(R.id.editProfile);
        MyApplication app = (MyApplication) getApplication();
        // get user to access auth email
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();


        MasterKey masterKey;
        try {
            masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SharedPreferences sharedPreferences;
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
        SharedPreferences.Editor editor = sharedPreferences.edit();

        MaterialToolbar appBar = findViewById(R.id.toolbar_profile);
        if (currUser != null){
            // eliminate poor regex
            String userId = currUser.getUid();
            String income = sharedPreferences.getString(userId, "");
            appBar.setTitle("Income: $" + income);
            if (income.isEmpty()){
                editProfile.setEnabled(false);
                createIncome.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            } else{
                editProfile.setEnabled(true);
                createIncome.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
            }

        }


        assert currUser != null;
        String userPath = currUser.getUid();
        // initialize db reference for access
        DatabaseReference userData = app.getDb().getReference("Users/"+ userPath);

        userData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot budgets, @Nullable String previousChildName) {
                Budget new_budget = (Budget)budgets.getValue(Budget.class);
                if (new_budget != null){
                    budgetList.add(new_budget);
                    int i = budgetList.indexOf(new_budget);
                    budgetAdapter.notifyItemInserted(i);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //not modifying data
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // not deleting individual data
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //ignore
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ignore
            }
        });

        FloatingActionButton logoutButton = findViewById(R.id.logout_button_profile);

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
                            Toast.makeText(ProfileActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                            firebaseAuth.removeAuthStateListener(this); // Remove the listener
                        }
                    }
                });
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_profile);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.budget_activity_container);
        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {
            int pageId = item.getItemId();

            if(pageId == R.id.navigation_budget){
                startActivity(new Intent(ProfileActivity.this, CreateBudgetActivity.class));
            }
            else if(pageId == R.id.navigation_profile) {
                Toast.makeText(this, "Already on profile page", Toast.LENGTH_SHORT).show();
            } else  {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }

        });


        createIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!createIncome.getText().toString().isEmpty()){
                    saveButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saveButton.setOnClickListener(view -> {
            editor.putString(userPath, createIncome.getText().toString());
            editor.apply();
            createIncome.setText("");
            editProfile.setEnabled(true);
            saveButton.setVisibility(View.INVISIBLE);
            createIncome.setVisibility(View.INVISIBLE);
        });

        editProfile.setOnClickListener(view -> {
            createIncome.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            editProfile.setEnabled(false);

        });

        deleteButton.setOnClickListener(view -> {
            int i = budgetList.size();
            budgetList.clear();
            budgetAdapter.notifyItemRangeRemoved(0, i);
            userData.removeValue();
        });

    }

}