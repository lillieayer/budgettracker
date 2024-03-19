package cs445.budgetapp.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import cs445.budgetapp.MainActivity;
import cs445.budgetapp.MyApplication;
import cs445.budgetapp.R;
import cs445.budgetapp.ui.budget.Budget;
import cs445.budgetapp.ui.budget.CreateBudgetActivity;

public class ProfileActivity extends AppCompatActivity {
    private MyApplication app;

    private DatabaseReference userData;

    private EditText createName, createIncome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createIncome = findViewById(R.id.editIncome);


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
        SharedPreferences.Editor editor = sharedPreferences.edit();


        List<Budget> budgetList = new ArrayList<>();
        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(budgetAdapter);

        app = (MyApplication) getApplication();
        // get user to access auth email
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();

        String[] userPathArr = currUser.getEmail().split("[@.]");
        String userPath = String.join("",userPathArr);
        // initialize db reference for access
        userData = app.getDb().getReference("Users/"+ userPath);

        // retrieve users past budgets
        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pastBudgets) {
                if (pastBudgets.exists()){
                    int numBudgets = 0;

                    for (DataSnapshot budget : pastBudgets.getChildren()){
                        Budget old_budget = budget.getValue(Budget.class);
                        budgetList.add(old_budget);
                        numBudgets++;
                    }
                    budgetAdapter.notifyItemRangeInserted(0,numBudgets);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                Budget old_budget = (Budget)snapshot.getValue(Budget.class);
                int i = budgetList.indexOf(old_budget);
                budgetList.remove(old_budget);
                budgetAdapter.notifyItemRemoved(i);

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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(createIncome.getText().toString() != null){
                    editor.putString(userPathArr[0], createIncome.getText().toString());
                    editor.apply();
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userData = null;
        app = null;
    }
}